package client.model.streamclients;

import java.io.IOException;

import client.view.profilemanagingwindow.LoadedProfile;
import lib.Debug;

public class ClientToServerStreamClient extends ProtoClient{

	private LoadedProfile currentlyLoadedProfile;
	
	/**
	 * Test Main Function
	 * Usable to test StreamClientConnection
	 * @param args not used
	 */
	public static void main(String args[]) {
		Debug.debug("Starte client to server StreamClient");
		
		for(int index = 0; index < 1; index++) {
			int clientID = 2 + index;
			LoadedProfile loadedProfile = new LoadedProfile("Profil 1");
			
			Debug.debug("LoadedProfile:");
			Debug.debug(loadedProfile.toString());
			
			ClientToServerStreamClient clientToServerStreamClient = new ClientToServerStreamClient("127.0.0.1", 2709, clientID, loadedProfile);
			Thread clientToServerStreamClientThread = new Thread(clientToServerStreamClient, "TestClientUpstream");
			clientToServerStreamClientThread.start();
		}
	}
	
	public ClientToServerStreamClient(String ip, int port, int clientID, LoadedProfile currentlyLoadedProfile) {
		super(ip, port, clientID);
		this.currentlyLoadedProfile = currentlyLoadedProfile;
		this.currentlyLoadedProfile.setClientID(clientID);
	}
	
	public void run() {
		schreibeNachricht(socket, this.clientID);
		schreibeNachricht(socket, this.currentlyLoadedProfile);
		
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
