package server;
/*
 * genutzte java version: 1.8.0_65
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Klasse zum starten des Wetter-Server der sich um die Clients kümmert
 */
public class GFPServer {
	public static void main(String[] args) throws Exception {

		final ExecutorService pool;
		ServerSocket serverSocket = null;
		int port = 2709;
		pool = Executors.newCachedThreadPool();

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Ein anderer Service läuft bereits auf diesem Port, bitte schließen um diesen zu starten!");
			System.exit(1);
		}

		Thread networkServiceThread = new Thread(new NetworkService(pool, serverSocket));
		System.out.println("Network service succesfully started, can now receive and process requests.");
		networkServiceThread.start();
		
		/*
		Thread dataCleanUpServiceThread = new Thread(new CleanUpService(pool, serverSocket));
		System.out.println("cleanup service succesfully started, decayed clients will now be removed");
		dataCleanUpServiceThread.start();
		*/
	}
}

class NetworkService implements Runnable {
	private StoredData storedData = new StoredData();
	int nextClientID;
	
	private final ServerSocket serverSocket;
	private final ExecutorService pool;
	private final boolean PIGS_DONT_FLY = true;

	public NetworkService(ExecutorService pool, ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.pool = pool;
		nextClientID = 1;
	}

	public void run() {
		try {
			while (PIGS_DONT_FLY) {
				Socket cs = serverSocket.accept();
				System.out.println("New socket established.");
				pool.execute(new Handler(cs, storedData, nextClientID));
				nextClientID++;
			}
		} catch (IOException e) {
			System.out.println("--- Interrupt NetworkService-run");
		}
	}
}


class CleanUpService implements Runnable {
	private StoredData storedData = new StoredData();

	public CleanUpService() {

	}

	public void run() {
		while (true) {
			//delete old profiles
		}
	}
}
