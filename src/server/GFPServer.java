package server;
/*
 * genutzte java version: 1.8.0_65
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/*
 * Klasse zum starten des Wetter-Server der sich um die Clients kümmert
 */
public class GFPServer {
	public static void main(String[] args) throws Exception {

		//final ExecutorService pool = Executors.newCachedThreadPool();
		ServerSocket serverSocket = null;
		int port = 2709;

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Ein anderer Service läuft bereits auf diesem Port, bitte schließen um diesen zu starten!");
			System.exit(1);
		}

		/*
		Thread clientUpdateService = new Thread(new ClientUpdateService(serverSocket));
		System.out.println("Network service succesfully started, can now receive and process requests.");
		clientUpdateService.start();
		*/
		
		Thread networkServiceThread = new Thread(new NetworkService(serverSocket));
		System.out.println("Network service succesfully started, can now receive and process requests.");
		networkServiceThread.start();
	}
}

class NetworkService implements Runnable, Observer {
	
	private final ServerSocket serverSocket;
	//private final ExecutorService pool;
	private final boolean PIGS_DONT_FLY = true;
	private LinkedList<Handler> allClientHandler;
	//private LinkedList<Integer> toBeRemovedClients;
	
	public StoredData storedData;

	public NetworkService(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.allClientHandler = new LinkedList<Handler>();
		this.storedData = new StoredData();
	}

	public void run() {
		try {
			while (PIGS_DONT_FLY) {
				/*
				 * Wait for new Clients
				 */
				Socket cs = serverSocket.accept();
				System.out.println("New socket established.");

				/*
				 * Create ClientHandler and
				 * Save them in the LinkedList for Future Refernce (mostly to send them the ServerToClientMessage)
				 */
				Handler clientHandler = new Handler(cs, this);
				allClientHandler.add(clientHandler);
				
				/*
				 * Create and start a Thread for the ClientHandler
				 */
				Thread clientHandlerThread = new Thread(clientHandler);
				clientHandlerThread.start();
				
			}
		} catch (IOException e) {
			System.out.println("Interrupt NetworkService-run");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		storedData.getLoadedProfileList().clear();
		
		int clientHandlerSize = allClientHandler.size();
		for(int index = 0; index < clientHandlerSize; index++) {
			//bau die zu sendenden daten (einfach: durch alle upstreams gehen und einmal alle profile einsammeln, aber teuer)
			//besseres System zum aktuell halten der Profile finden //TODO
			if(allClientHandler.get(index).getClientType().equals("Upstream")) {
				storedData.getLoadedProfileList().add(allClientHandler.get(index).getLoadedProfile());
			}
		}
		/**
		 * Debug Info:
		 */
		System.out.println("DEBUG: " + storedData.getLoadedProfileList().size() + " Profiles are recognized.");
		for (int index = 0; index < storedData.getLoadedProfileList().size(); index++) {
			System.out.println(storedData.getLoadedProfileList().get(index));
		}
		//dann senden
		for(int index = 0; index < clientHandlerSize; index++) {
			if(allClientHandler.get(index).getClientType().equals("Downstream")) {
				//profile bereitstellen in der server to client message
				allClientHandler.get(index).setNewProfilesAvailable(true);
			}
		}
	}
	
	public void removeHandler(Handler toBeRemoved) {
		int clientHandlerSize = allClientHandler.size();
		for(int index = 0; index < clientHandlerSize; index++) {
			if (allClientHandler.get(index).getClientID() == toBeRemoved.getClientID()) {
				allClientHandler.remove(index);
			}
		}
	}
	
	public void updateStoredClients() {
		
	}
}

/*
class ClientUpdateService implements Runnable, Observer {
	
	private ConcurrentLinkedQueue<LoadedProfile> serverToClientMessage_allProfiles;
	
	public ClientUpdateService(ServerSocket serverSocket) {
		serverToClientMessage_allProfiles = new ConcurrentLinkedQueue<>();
	}

	@Override
	public void run() {
		
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
*/