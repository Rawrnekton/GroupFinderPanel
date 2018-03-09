package server;
/**
 * @author jg
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import client.view.profilemanagingwindow.LoadedProfile;

public class GFPServer {
	public static void main(String[] args) throws Exception {

		// final ExecutorService pool = Executors.newCachedThreadPool();
		ServerSocket serverSocket = null;
		int port = 2709;

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println(
					"Ein anderer Service läuft bereits auf diesem Port, bitte schließen um diesen zu starten!");
			System.exit(1);
		}

		/*
		 * Thread clientUpdateService = new Thread(new
		 * ClientUpdateService(serverSocket)); System.out.
		 * println("Network service successfully started, can now receive and process requests."
		 * ); clientUpdateService.start();
		 */

		Thread networkServiceThread = new Thread(new NetworkService(serverSocket), "networkServiceThread");
		System.out.println("Network service succesfully started, can now receive and process requests.");
		networkServiceThread.start();
	}
}

class NetworkService implements Runnable {

	private final ServerSocket serverSocket;
	private final boolean PIGS_DONT_FLY = true;
	
//	private ExecutorService test = Executors.newCachedThreadPool();
	
	private LinkedList<Handler> allServerToClientHandler;
	private LinkedList<LoadedProfile> clientProfiles;

	public NetworkService(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.allServerToClientHandler = new LinkedList<Handler>();
		this.clientProfiles = new LinkedList<LoadedProfile>();
		// this.storedData = new StoredData();
	}

	public void run() {
		try {
			while (PIGS_DONT_FLY) {
				/*
				 * Wait for new Clients
				 */
				Socket cs = serverSocket.accept();
				lib.Debug.debug(this, "New socket established.", false);
				/*
				 * Create ClientHandler and Save them in the LinkedList for Future Reference
				 * (mostly to send them the ServerToClientMessage)
				 */
				Handler clientHandler = new Handler(cs, this);
				// allServerToClientHandler.add(clientHandler); // questionable, removed for now

				/*
				 * Create and start a Thread for the ClientHandler
				 */				
				Thread clientHandlerThread = new Thread(clientHandler, "clientHandler");
				clientHandlerThread.start();

			}
		} catch (IOException e) {
			System.out.println("Interrupt NetworkService-run");
		}
	}


	/**
	 * Function that organizes the profiles of connected clients
	 * Adds them together
	 * 
	 */
//	@Override
	public void updateClientData(LoadedProfile loadedProfile) {

		LoadedProfile newProfile = loadedProfile;

		
		
		/*
		 * updateClientData gets called with (this, null) only when the server to client broke and the
		 * profile of the broken client got deleted
		 * 
		 * thats why all clients get updated, even though no "new" information is present
		 */
		if (loadedProfile == null) {
			
			int serverToClientHandlerCount = allServerToClientHandler.size();
			for (int index = 0; index < serverToClientHandlerCount; index++) {
				allServerToClientHandler.get(index).setNewProfilesAvailable(true);
			}
			
			return;
		}

		lib.Debug.debug(this, "profilename: " + "\"" + newProfile.getProfileName() + "\"", false);

		/**
		 * Zusammensetzen der aktuellen Profilliste
		 */

		int loadedProfilesCount = clientProfiles.size();
		for (int index = 0; index < loadedProfilesCount; index++) {
			if (clientProfiles.get(index).getClientID() == newProfile.getClientID()) {
				lib.Debug.debug(this,
						"trying to delete profile with ID " + newProfile.getClientID() + " at index " + index, true);
				try {
					clientProfiles.remove(index);
				} catch (IndexOutOfBoundsException e) {
					lib.Debug.debug(this, "yeah whatever", false);
				}
			}
		}

		clientProfiles.add(newProfile);
		lib.Debug.debug(this, "clientProfiles = " + clientProfiles.size());
		lib.Debug.debug(this, getProfileOverview(), true);		
		/*
		 * Senden der aktuellen Profilliste, bzw notifizieren dass eine neue
		 * Profilliste da ist
		 */
		int serverToClientHandlerCount = allServerToClientHandler.size();
		lib.Debug.debug(this, "serverToClientHandlerCount = " + serverToClientHandlerCount, false);
		
		/*
		 * tell the server to client streams that they should update
		 */
		for (int index = 0; index < serverToClientHandlerCount; index++) {
			allServerToClientHandler.get(index).setNewProfilesAvailable(true);
		}
	}

	public String getProfileOverview() {
		String returnString = "-- profile overview --\n";
		int clientProfilesCount = clientProfiles.size();

		for (int index = 0; index < clientProfilesCount; index++) {
			returnString += clientProfiles.get(index).getClientID() + "	| " + clientProfiles.get(index).getProfileName()
					+ "	|\n";
		}

		return returnString;
	}

	/* ---------- GET-METHODS ---------- */
	public LinkedList<Handler> getAllServerToClientHandler() {
		return this.allServerToClientHandler;
	}

	public LinkedList<LoadedProfile> getClientProfiles() {
		return this.clientProfiles;
	}
	/*
	 * public void removeHandler(Handler toBeRemoved) { int clientHandlerSize =
	 * allServerToClientHandler.size(); for(int index = 0; index <
	 * clientHandlerSize; index++) { if
	 * (allServerToClientHandler.get(index).getClientID() ==
	 * toBeRemoved.getClientID()) { allServerToClientHandler.remove(index); } } }
	 */
}

/*
 * class ClientUpdateService implements Runnable, Observer {
 * 
 * private ConcurrentLinkedQueue<LoadedProfile>
 * serverToClientMessage_allProfiles;
 * 
 * public ClientUpdateService(ServerSocket serverSocket) {
 * serverToClientMessage_allProfiles = new ConcurrentLinkedQueue<>(); }
 * 
 * @Override public void run() {
 * 
 * }
 * 
 * @Override public void update(Observable o, Object arg) {
 * 
 * } }
 */