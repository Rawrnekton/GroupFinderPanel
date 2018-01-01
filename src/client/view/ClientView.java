package client.view;

import java.util.Observer;

import client.view.mainwindow.MainWindowBase;
import javafx.stage.Stage;

/*
 * Sets up View for application and manages the displayed content
 * that is supplied by the controller
 */
public class ClientView{

	private MainWindowBase mainWindowBase;
	private Stage primaryStage;
	private Observer observer;
	
	
	public ClientView(Stage primaryStage, Observer observer) {
		this.primaryStage = primaryStage;
		this.observer = observer;
		this.mainWindowBase = new MainWindowBase(this.primaryStage, this.observer);
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public MainWindowBase getMainWindowBase() {
		return this.mainWindowBase;
	}
}