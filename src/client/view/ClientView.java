package client.view;

import java.util.Observer;

import client.view.mainwindow.MainWindowBase;
import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.stage.Stage;

/*
 * Sets up View for application and manages the displayed content
 * that is supplied by the controller
 */
public class ClientView{

	private MainWindowBase mainWindowBase;
	private ProfileManagingWindowBase profileManagingWindowBase;
	private Stage primaryStage;
	private Observer observer;
	
	
	public ClientView(Stage primaryStage, Observer observer) {
		this.primaryStage = primaryStage;
		this.observer = observer;
		this.mainWindowBase = new MainWindowBase(this.primaryStage, this.observer);
		this.profileManagingWindowBase = null;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public MainWindowBase getMainWindowBase() {
		return mainWindowBase;
	}

	public ProfileManagingWindowBase getProfileManagingWindowBase() {
		return profileManagingWindowBase;
	}

	public void setProfileManagingWindowBase(ProfileManagingWindowBase profileManagingWindowBase) {
		this.profileManagingWindowBase = profileManagingWindowBase;
	}
	
}