package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import client.view.profilemanagingwindow.LoadedProfile;

/**
 * 
 * @author jg
 *
 */
public class Handler implements Runnable {
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
	 * debug string to get the used client type
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
	 * the protocol for transmission will be: while true: receive clients profile
	 * send all profiles -> to get the new profiles, the client needs to send his
	 * profile
	 * 
	 * client.isClosed()
	 */
	public void run() {
		// check if upstream or downstream

		// statusMessage = "Clienthandler started.";
		// System.out.println(statusMessage);

		try {
			lib.Debug.debug("reading clientID");
			// clientID = (int) inFromClientStream.readObject();
			clientID = inFromClientStream.readInt();

			lib.Debug.debug("received ID = " + clientID);
			/*
			 * if ID is zero: this is the first thread that the client connected with ->
			 * give him a real ID
			 *
			 * setup here is slightly hacky tbh
			 */
			if (clientID == 0) {
				lib.Debug.debug(this, "new client detected");
				clientType = "serverToClientStream";
				serverToClientStream();
			} else {
				clientType = "clienToServerStream";

				// save offered ID in the downstream to prevent meddling with the id
				clientToServerStream(clientID);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Client To Server Stream
	 * 
	 * @param clientID
	 *            failsafe to prevent client from transmitting two different IDs
	 */
	private void clientToServerStream(int clientID) {
		/*
		 * Client teilt Server Profile mit Sever wartet danach auf ein erneutes
		 * schreiben auf den Socket
		 */
		try {
			// New Value gets collected by the Observer
			loadedProfile = (LoadedProfile) inFromClientStream.readObject();

			/*
			 * failsafe to prevent client from transmitting two different IDs
			 */
			loadedProfile.setClientID(clientID);

			/*
			 * Check ob die ID überhaupt einen Downstream hat TODO checkForDownstream(...)
			 * always returns true at the moment
			 */
			if (checkForDownstream(loadedProfile.getClientID())) {
				lib.Debug.debug(this, "New profile detected.", true);
				lib.Debug.debug(this, "Running updating all clients", true);

				this.networkServiceThread.updateClientData(this.loadedProfile);
			}

			lib.Debug.debug(this, "Updates were succesful", true);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

	private boolean checkForDownstream(int clientID) {
		// TODO look up if a downstream exists or not
		return true;
	}

	/**
	 * Server to Client Stream Sends all of the gathered Profiles to the Client
	 */
	private void serverToClientStream() {

		try {
			/*
			 * optimize this to make removing things easier server starts at 0 and then
			 * preincrements the ID everytime a new Client connects does not reuse id's yet
			 * and i believe it will stay that way
			 */
			clientID = ++nextClientID;
			outToClientStream.writeInt(clientID);
			lib.Debug.debug(this, "new clientID = " + clientID, true);

			networkServiceThread.getAllServerToClientHandler().add(this);

			while (true) {
				try {
					Thread.sleep(delayInMS);
					if (newProfilesAvailable) {
						newProfilesAvailable = false;
						outToClientStream.reset();
						// serverToClientMessage.setLoadedProfileList(networkServiceThread.getClientProfiles());
						// //storedData.getLoadedProfileList());
						lib.Debug.debug(this, "sending " + networkServiceThread.getClientProfiles().size()
								+ " Profiles to ID " + clientID, true);
						ServerToClientMessage msg = new ServerToClientMessage();
						msg.setClientID(clientID);
						msg.setLoadedProfileList(networkServiceThread.getClientProfiles());
						// outToClientStream.writeObject(networkServiceThread.getClientProfiles());
						outToClientStream.writeObject(msg);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					lib.Debug.debug(this, "Downstream mit ID " + clientID + " starb.", false);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Wenn Clientverbindung abbricht, oder geschlossen wird, wird noch das
		 * gespeicherte Profil des Clients gelöscht andere downstreams anstupsen, dass
		 * "neue" profile da
		 */
		deleteProfilOf(clientID);

		networkServiceThread.updateClientData(null);
	}

	/**
	 * Deletes the profile of the given ID
	 * 
	 * @param clientID
	 */
	private void deleteProfilOf(int clientID) {
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