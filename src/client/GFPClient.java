package client;

import java.util.Observable;
import java.util.Observer;

import client.model.ClientBase;
import client.view.ClientView;
import client.view.manageProfileView.LoadedProfile;
import client.view.manageProfileView.ProfilManageView;
import javafx.application.Application;
import javafx.stage.Stage;

public class GFPClient extends Application implements Observer{
	
	public static void main(String[] args) {
			
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		@SuppressWarnings("unused")
		ClientView testView = new ClientView(primaryStage, this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ProfilManageView) {
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