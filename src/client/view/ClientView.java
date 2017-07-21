package client.view;

import java.util.Observer;

import client.view.mainwindow.MainWindowBase;
import javafx.stage.Stage;

/*
 * Sets up View for applicationa and manages the displayed content
 * that is supplied by the controller
 */
public class ClientView{

	/*
	 * Setup initial View
	 */
	public ClientView(Stage primaryStage, Observer observer) {
		@SuppressWarnings("unused")
		MainWindowBase primaryStageView = new MainWindowBase(primaryStage, observer);
	}
}
