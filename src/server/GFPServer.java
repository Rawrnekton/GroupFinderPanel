package server;
/*
 * genutzte java version: 1.8.0_65
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.view.profilemanagingwindow.LoadedProfile;

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
	
	private StoredData storedData;

	public NetworkService(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.allClientHandler = new LinkedList<Handler>();
		this.storedData = new StoredData();
	}

	public void run() {
		try {
			while (PIGS_DONT_FLY) {
				Socket cs = serverSocket.accept();
				System.out.println("New socket established.");
				Handler ClientHandler = new Handler(cs, this);
				allClientHandler.add(ClientHandler);
			}
		} catch (IOException e) {
			System.out.println("Interrupt NetworkService-run");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		int clientHandlerSize = allClientHandler.size();
		storedData.getLoadedProfileList().clear();
		for( int index = 0; index < clientHandlerSize; index++) {
			//bau die zu sendenden daten (einfach: durch alle upstreams gehen und einmal alle profile einsammeln)
			//dann senden
			if(allClientHandler.get(index).getClientType().equals("Upstream")) {
				storedData.getLoadedProfileList().add(allClientHandler.get(index).getLoadedProfile());
			}
		}
	}
}

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

