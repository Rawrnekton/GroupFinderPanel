package client.view;

import java.util.Observer;

import client.view.primaryView.PrimaryStageView;
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
		PrimaryStageView primaryStageView = new PrimaryStageView(primaryStage, observer);
	}
}
