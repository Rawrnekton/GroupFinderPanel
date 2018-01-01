package client.model.streamclients;

import java.io.IOException;

import client.view.profilemanagingwindow.LoadedProfile;

public class ClientToServerStreamClient extends ProtoClient{

	private LoadedProfile currentlyLoadedProfile;
	
	/**
	 * Test Main Function
	 * Usable to test StreamClientConnection
	 * @param args not used
	 */
	public static void main(String args[]) {
		for(int index = 0; index < 1; index++) {
			int clientID = 17 + index;
			LoadedProfile test = new LoadedProfile("Profil 2");
			
			ClientToServerStreamClient clientToServerStreamClient = new ClientToServerStreamClient("127.0.0.1", 2709, clientID, test);
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
