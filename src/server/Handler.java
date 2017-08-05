package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.view.profilemanagingwindow.LoadedProfile;

public class Handler implements Runnable {
	private final Socket client;
	ObjectInputStream inFromClientStream;
	ObjectOutputStream outToClientStream;
	LoadedProfile loadedProfile;
	StoredData storedData;
	ServerToClientMessage serverToClientMessage;
	int nextClientID;
	

	Handler(Socket client, StoredData storedData, int nextClientID) {
		this.client = client;
		this.storedData = storedData;
		this.nextClientID = nextClientID;
		serverToClientMessage = new ServerToClientMessage();
	}

	public void run() {
		try {
			inFromClientStream = new ObjectInputStream(client.getInputStream());
			outToClientStream = new ObjectOutputStream(client.getOutputStream());

			System.out.println("Clienthandler startet");

			LoadedProfile inFromClientObject = (LoadedProfile) inFromClientStream.readObject();
			System.out.println(inFromClientObject.getProfileName() + " wurde empfangen.");

			loadedProfile = inFromClientObject;

			if (loadedProfile.getClientID() == 0) loadedProfile.setClientID(nextClientID);
			storedData.addProfile(loadedProfile);
			
			serverToClientMessage.setNextClientID(nextClientID);
			serverToClientMessage.setLoadedProfileList(storedData.getLoadedProfileList());
		} catch (IOException e) {
			System.out.println("IOException, Fehler beim lesen der Nachricht vom Client");
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		} finally {
			try {
				outToClientStream.writeObject(this.serverToClientMessage);
				System.out.println("Client Handler is done");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (!client.isClosed()) {
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
}