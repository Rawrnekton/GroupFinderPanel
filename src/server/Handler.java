package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

import client.view.profilemanagingwindow.LoadedProfile;

public class Handler extends Observable implements Runnable {
	private final Socket client;
	private final NetworkService networkServiceThread;
	
	private ObjectInputStream inFromClientStream;
	private ObjectOutputStream outToClientStream;
	
	private LoadedProfile loadedProfile;
	private StoredData storedData;
	private ServerToClientMessage serverToClientMessage;
	
	
	/**
	 * Debug Value, that is used to communicate the current State to the Console
	 */
	private String statusMessage = new String();
	private boolean newProfilesAvailable;
	
	private static int nextClientID = 0;
	private int thisClientID;
	/*
	 * Either Upstream or Downstream
	 * Um es zu vereinfachen: der server ist oben
	 * also upstream ist: data vom client zum server
	 * downstream: data vom server zum client
	 */
	private String clientType;
	
	Handler(Socket client, NetworkService networkServiceThread) {
		this.client = client;
		this.networkServiceThread = networkServiceThread;
		addObserver(networkServiceThread);
		
		try {
			inFromClientStream = new ObjectInputStream(this.client.getInputStream());
			outToClientStream = new ObjectOutputStream(this.client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * the protocol for transmission will be:
	 * while true:
	 * receive clients profile
	 * send all profiles
	 * -> to get the new profiles, the client needs to send his profile
	 * 
	 * client.isClosed()
	 */
	public void run() {
		//check if upstream or downstream
		
		statusMessage = "Clienthandler started.";
		System.out.println(statusMessage);
		
		try {
			thisClientID = (int) inFromClientStream.readObject();

			/*
			 * if ID is zero: this is the first thread that the client connected with
			 * -> give him a real ID and wait for his profile,
			 * else: use it as downstream for all data rn
			 * 
			 * setup here is slightly hacky tbh
			 */
			if (thisClientID == 0) {
				clientType = "Upstream";
				executeUpStream();
			} else {
				clientType = "Downstream";
				executeDownStream();
			}			
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Client To Server exchange
	 */
	public void executeUpStream() {
		/*
		 * Client teilt Server Profile mit
		 * Sever wartet danach auf ein erneutes schreiben auf den Socket
		 */
		try {
			nextClientID++;
			outToClientStream.writeObject(nextClientID);

			while (true) {
				try {
					//New Value gets collected by the Observer
					loadedProfile = (LoadedProfile) inFromClientStream.readObject();
					notifyObservers();
					
					//evtl affirmation schicken, dass alles gut ist
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
					break;
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void executeDownStream() {
		//immer wenn der observer den handler anschubst
		//also den entsprechenden bool wert auf true setzt
		
		while (true) {
			try {
				Thread.sleep(1000);
				if (newProfilesAvailable) {
					newProfilesAvailable = false;
					serverToClientMessage.setLoadedProfileList(storedData.getLoadedProfileList());
					outToClientStream.writeObject(this.serverToClientMessage);
				}
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public LoadedProfile getLoadedProfile() {
		return this.loadedProfile;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
	
	public String getClientType() {
		return this.clientType;
	}
	
	public void updateStoredData(StoredData storedData) {
		this.storedData = storedData;
	}
	
	public void setMessage(ConcurrentLinkedQueue<LoadedProfile> serverToClientMessage_allProfiles) {
		
	}
	
	public void setNewProfilesAvailable(boolean newProfilesAvailable) {
		this.newProfilesAvailable = newProfilesAvailable;
	}
}