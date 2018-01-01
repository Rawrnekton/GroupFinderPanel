package client;

import java.util.Observable;
import java.util.Observer;

import client.controller.ClientController;
import client.model.ClientModel;
import client.model.streamclients.ServerToClientStreamClient;
import client.view.ClientView;
import client.view.profilemanagingwindow.LoadedProfile;
import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.application.Application;
import javafx.stage.Stage;


public class GFPClient extends Application implements Observer{
	
	/*
	 * Downstream zum empfangen der Serverdaten
	 */
	private ServerToClientStreamClient serverToClientStreamClient;
	private int ClientID;
	
	/**
	 * Start des Clients
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * Wird durch launch(args) aus der main gestartet
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ClientView clientView = new ClientView(primaryStage, this);
		ClientModel clientModel = new ClientModel();
		
		@SuppressWarnings("unused")
		ClientController clientController = new ClientController(clientView, clientModel);
		
//		clientController.registerEventsMain();
		/*
		String ip = lib.Config.ip; //"127.0.0.1"; // "vigor-mortis.ddns.net"; //will be configurable via profile
		int port = lib.Config.port;
		
		serverToClientStreamClient = new ServerToClientStreamClient(ip, port, 0);
		Thread serverToClientStreamClientThread = new Thread(serverToClientStreamClient, "ServerToClientStreamClientThread");
		serverToClientStreamClientThread.start();
		
		/*
		 * If something causes weird timing issues its probably this little *thing* here 
		 * /
		while (this.ClientID == 0) {
			Thread.yield();
			this.ClientID = serverToClientStreamClient.getClientID();
		}
		*/
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ProfileManagingWindowBase) {
			LoadedProfile loadedProfile = (LoadedProfile) arg;
			System.out.println(loadedProfile.getProfileName() + " ist geladen und wird gesendet.");	
			//System.out.println(loadedProfile.toString());
			
			try {
				@SuppressWarnings("unused")
				ClientModel clientBase = new ClientModel(loadedProfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/* ---------- GET-METHODS ---------- */
	public int getClientID() throws Exception{
		if (this.ClientID != 0) {
			return this.ClientID;
		} else {
			throw new Exception();
		}
	}
	
	public ServerToClientStreamClient getServerToClientStreamClient() {
		return this.serverToClientStreamClient;
	}
	
	/* ---------- MISC-METHODS --------- */
	
}