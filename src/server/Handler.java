package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

import client.view.profilemanagingwindow.LoadedProfile;

/**
 * Muss besser in down und upstream geteilt werden
 * 
 * @author jonathan
 *
 */
public class Handler extends Observable implements Runnable {
	private final Socket client;
	private final NetworkService networkServiceThread;

	private ObjectInputStream inFromClientStream;
	private ObjectOutputStream outToClientStream;

	private LoadedProfile loadedProfile;

	/**
	 * Debug Value, that is used to communicate the current State to the Console
	 */
	private String statusMessage = new String();
	private boolean newProfilesAvailable;

	private static int nextClientID = 0;
	private int clientID;

	private int delayInMS = 10;
	/*
	 * Either Upstream or Downstream Um es zu vereinfachen: der server ist oben
	 * upstream hier: data vom client zum server downstream hier: data vom
	 * server zum client
	 */
	private String clientType;

	Handler(Socket client, NetworkService networkServiceThread) {
		this.client = client;
		this.networkServiceThread = networkServiceThread;

		try {
			inFromClientStream = new ObjectInputStream(this.client.getInputStream());
			outToClientStream = new ObjectOutputStream(this.client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * the protocol for transmission will be: while true: receive clients
	 * profile send all profiles -> to get the new profiles, the client needs to
	 * send his profile
	 * 
	 * client.isClosed()
	 */
	public void run() {
		//check if upstream or downstream

		statusMessage = "Clienthandler started.";
//		System.out.println(statusMessage);

		try {
			lib.Debug.debug("reading clientID");
			clientID = (int) inFromClientStream.readObject();

			lib.Debug.debug("received ID = " + clientID);
			/*
			 * if ID is zero: this is the first thread that the client connected
			 * with -> give him a real ID
			 *
			 * setup here is slightly hacky tbh
			 */
			if (clientID == 0) {
				lib.Debug.debug(this, "new client detected");
				clientType = "serverToClientStream";
				serverToClientStream();
			} else {
				clientType = "clienToServerStream";
				clientToServerStream();
			}
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Client To Server Stream
	 */
	private void clientToServerStream() {
		/*
		 * Client teilt Server Profile mit Sever wartet danach auf ein erneutes
		 * schreiben auf den Socket
		 */
		try {
			//New Value gets collected by the Observer
			loadedProfile = (LoadedProfile) inFromClientStream.readObject();

			/*
			 * Check ob die ID überhaupt einen Downstream hat
			 * TODO checkForDownstream(...) always returns true
			 */
			if (checkForDownstream(loadedProfile.getClientID())) {
				lib.Debug.debug(this, "New profile detected.", true);
				lib.Debug.debug(this, "Running client updates", true);
				
				this.networkServiceThread.updateClientData(this, this.loadedProfile);
			}

			//evtl affirmation schicken, if all good
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	private boolean checkForDownstream(int clientID) {
		// TODO look up if a downstream exists or not
		return true;
	}

	/**
	 * Server to Client Stream
	 */
	private void serverToClientStream() {
		//immer wenn der observer den handler anschubst
		//also den entsprechenden bool wert auf true setzt

		try {
			/*
			 * optimize this to make removing things easier
			 * server starts at 0 and then preincrements the ID everytime a new
			 * Client connects
			 * does not reuse id's yet and i believe it will stay that way because
			 * there is no functionality lost
			 */
			clientID = ++nextClientID;
			outToClientStream.writeObject(clientID);
			lib.Debug.debug(this, "new clientID = " + clientID, true);
			
			networkServiceThread.getAllServerToClientHandler().add(this);

			while (true) {
				try {
					Thread.sleep(delayInMS);
					if (newProfilesAvailable) {
						newProfilesAvailable = false;
						outToClientStream.reset();
//						serverToClientMessage.setLoadedProfileList(networkServiceThread.getClientProfiles()); //storedData.getLoadedProfileList());
						lib.Debug.debug(this, "sending " + networkServiceThread.getClientProfiles().size() + " Profiles to ID " + clientID, true);
						ServerToClientMessage msg = new ServerToClientMessage();
						msg.setClientID(clientID);
						msg.setLoadedProfileList(networkServiceThread.getClientProfiles());
//						outToClientStream.writeObject(networkServiceThread.getClientProfiles());
						outToClientStream.writeObject(msg);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					lib.Debug.debug(this, "Downstream mit ID " + clientID + " starb", false);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Wenn Clientverbindung abbricht, oder geschlossen wird, wird noch das
		 * gespeicherte Profil des Clients gelöscht
		 * andere downstreams anstupsen, dass "neue" profile da
		 */
		deleteProfilOf(clientID);

		setChanged();
		notifyObservers();
	}

	/**
	 * Deletes the profile of the given ID
	 * 
	 * @param clientID
	 */
	private void deleteProfilOf(int clientID) {
		//TODO Write some basic functionality to test and make it robust, i dont want the server to freak out every time something goes wrong
		int handlerAmount = networkServiceThread.getAllServerToClientHandler().size();
		lib.Debug.debug(this, "handlerAmount = " + handlerAmount);
		lib.Debug.debug(this, "to be deleted ID = " + clientID, true);
		for (int index = 0; index < handlerAmount; index++) {
			if (networkServiceThread.getAllServerToClientHandler().get(index).clientID == clientID) {
				networkServiceThread.getAllServerToClientHandler().remove(index);
			}
		}
		
	}

	/* ---------- GET-METHODS ---------- */
	public LoadedProfile getLoadedProfile() {
		return this.loadedProfile;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public String getClientType() {
		return this.clientType;
	}

	public int getClientID() {
		return this.clientID;
	}

	public void setMessage(ConcurrentLinkedQueue<LoadedProfile> serverToClientMessage_allProfiles) {

	}

	public void setNewProfilesAvailable(boolean newProfilesAvailable) {
		this.newProfilesAvailable = newProfilesAvailable;
	}
}