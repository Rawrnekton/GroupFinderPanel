package client;

import java.util.Observable;
import java.util.Observer;

import client.model.ClientBase;
import client.view.ClientView;
import client.view.profilemanagingwindow.LoadedProfile;
import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.application.Application;
import javafx.stage.Stage;

public class GFPClient extends Application implements Observer{
	
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
		@SuppressWarnings("unused")
		ClientView testView = new ClientView(primaryStage, this);
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
				ClientBase clientBase = new ClientBase(loadedProfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}