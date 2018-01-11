package client.controller.events.mainwindow;

import client.view.profilemanagingwindow.LoadedProfile;
import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import lib.Debug;
import lib.Misc;

public class ManageProfileBtnEvent implements EventHandler<ActionEvent> {

	Stage primaryStage;
	ProfileManagingWindowBase profileManagingWindowBase;

	public ManageProfileBtnEvent(Stage primaryStage) {
		Debug.debug(this, "Created the profileManagingWindowBaseEvent.");
		this.primaryStage = primaryStage;
	}

	@Override
	public void handle(ActionEvent event) {
		Debug.debug(this, "Creating a profileManagementWindow.");
		
		String preloadedProfile = Misc.LASTLOADEDPROFILE;
		
		profileManagingWindowBase = new ProfileManagingWindowBase(this.primaryStage);
		profileManagingWindowBase.setLoadedProfile(new LoadedProfile(preloadedProfile));
		profileManagingWindowBase.setBottomPane();
		profileManagingWindowBase.setTextFields();
		profileManagingWindowBase.updateComboBoxContent();
	}
}
