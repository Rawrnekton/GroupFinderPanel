package server;
/*
 * genutzte java version: 1.8.0_65
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.view.profilemanagingwindow.LoadedProfile;

/*
 * Klasse zum starten des Wetter-Server der sich um die Clients kümmert
 */
public class GFPServer {
	
	public static void main(String[] args) throws Exception {
		
		final ExecutorService pool;
		ServerSocket serverSocket = null;
		int port = 2709;
		pool = Executors.newCachedThreadPool();
		
		try{
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Ein anderer Service läuft bereits auf diesem Port, bitte schließen um diesen zu starten!");
			System.exit(1);
		}
		
		Thread networkServiceThread = new Thread(new NetzwerkService(pool, serverSocket));
		System.out.println("Network service succesfully started, can now receive and process requests.");
		networkServiceThread.start();
	}
}

class NetzwerkService implements Runnable {
	
	private final ServerSocket serverSocket;
	private final ExecutorService pool;
	private static final boolean PIGS_DONT_FLY = true;

	public NetzwerkService(ExecutorService pool, ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.pool = pool;
	}
	
	public void run() {
		try {
			while(PIGS_DONT_FLY) {
				Socket cs = serverSocket.accept();
				System.out.println("New socket established.");
				pool.execute(new Handler(cs));
			}
		} catch (IOException e) {
			System.out.println("--- Interrupt NetworkService-run");
		}
	}
}

class Handler implements Runnable {
	private final Socket client;
	ObjectInputStream inFromClientStream;
	ObjectOutputStream outToClientStream;
	
	Handler(Socket client) {
		this.client = client;
	}
	
	public void run() {
		String rt = "";
		
		try {
			inFromClientStream = new ObjectInputStream(client.getInputStream());
			outToClientStream = new ObjectOutputStream(client.getOutputStream());

			System.out.println("Clienthandler arbeitet");
			
			LoadedProfile inFromClientObject = (LoadedProfile) inFromClientStream.readObject();
			System.out.println(inFromClientObject.getProfileName() + " wurde gesendet.");
			rt = inFromClientObject.getProfileName();
			
		} catch (IOException e) {
			System.out.println("IOException, Fehler beim lesen der Nachricht vom Client");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				outToClientStream.writeObject(rt);
				System.out.println("Server Antwortet mit " + rt);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!client.isClosed()) {
				System.out.println("Client wird vom Server geschlossen.");
				try {
					client.close();
				} catch (IOException e) {
					//eh
				}
			}
			System.out.println("=================");
		}
	}
	
	String getWetter(String stadt) {
		String wetter;
			switch(stadt){
				case "Leipzig": 	wetter = "Client fragt nach Wetter in Leipzig, Server antwortet „Sonnig, 20 Grad“.";
									break;
				case "FunkyTown": 	wetter = "Client fragt nach Wetter in FunkyTown, Server antwortet „Sonnig, 23 Grad“.";
									break;
				case "Hamburg":		wetter = "Client fragt nach Wetter in Hamburg, Server antwortet „Windig, 18 Grad“.";
									break;
				case "Erlangen":	wetter = "Client fragt nach Wetter in Erlangen, Server antwortet „Regen, 19 Grad“.";
									break;
				case "Konstanz":	wetter = "Client fragt nach Wetter in Konstanz, Server antwortet „Neblig, 24 Grad“.";
									break;
				default: 		wetter = "Stadt nicht bekannt.\nBekannte Städte: Leipzig, Stuttgart, Hamburg, Erlangen und Konstanz.";
			}
		return wetter;
	}
}