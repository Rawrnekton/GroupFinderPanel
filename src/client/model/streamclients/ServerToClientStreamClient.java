package client.model.streamclients;

import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import client.view.profilemanagingwindow.LoadedProfile;
import lib.Debug;
import server.ServerToClientMessage;

class ServerToClientStreamClient extends ProtoClient {

	protected LinkedList<LoadedProfile> allLoadedProfiles;

	public static void main(String args[]) {
		ServerToClientStreamClient serverToClientStreamClient = new ServerToClientStreamClient("127.0.0.1", 2709, 0);
		Thread serverToClientStreamClientThread = new Thread(serverToClientStreamClient, "TestClientDownStream");
		serverToClientStreamClientThread.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("ClientID ist: " + serverToClientStreamClient.getClientID());
		
		System.out.println("Warten auf alle Profile.");
	
		/*
		while (true) {
			System.out.println("Empfangene Profile:	" + serverToClientStreamClient.getAllLoadedProfiles());
			System.out.println("Downstream läuft:	" + serverToClientStreamClientThread.isAlive());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}
	
	public ServerToClientStreamClient(String ip, int port, int clientID) {
		super(ip, port, clientID);

		this.allLoadedProfiles = new LinkedList<LoadedProfile>();
	}

	@Override
	public void run() {
		schreibeNachricht(socket, 0);
		clientID = (int) leseNachricht(socket);
		
		try {
			while (true) {
				ServerToClientMessage serverMsg = null;
				serverMsg = (ServerToClientMessage) leseNachrichtThrowingError(socket);
				allLoadedProfiles = serverMsg.getLoadedProfileList();
				lib.Debug.debug("received profile count " + allLoadedProfiles.size());
				
				String debugString = "\n";
				for (int index = 0; index < allLoadedProfiles.size(); index++) {
					debugString += allLoadedProfiles.get(index).getClientID() + "	| " + allLoadedProfiles.get(index).getProfileName() + "	|\n";
				}
				
				Debug.debug(debugString);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Debug.debug("meh");
		}
	}

	/* ---------- GET-METHODS ---------- */
	public LinkedList<LoadedProfile> getAllLoadedProfiles() {
		return this.allLoadedProfiles;
	}
}