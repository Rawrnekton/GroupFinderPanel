package client.controller.events.mainwindow;

import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import lib.Debug;

public class ManageProfileBtnEvent implements EventHandler<ActionEvent>{

	Stage primaryStage;
//	TestWindow testWindow;
	ProfileManagingWindowBase profileManagingWindowBase;
	
	public ManageProfileBtnEvent(Stage primaryStage) {
		Debug.debug(this, "Created the profileManagingWindowBaseEvent.");
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void handle(ActionEvent event) {
		Debug.debug(this, "Creating a profileManagementWindow.");
//		testWindow = new TestWindow(this.primaryStage);
		profileManagingWindowBase = new ProfileManagingWindowBase(this.primaryStage);
	}

}
