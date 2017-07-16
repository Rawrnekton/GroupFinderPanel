package client.view;

import java.util.Observer;

import client.view.primaryview.TopPane;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 * Sets up View for applicationa and manages the displayed content
 * that is supplied by the controller
 */
public class ClientView{
	Stage primaryStage;

	/*
	 * Setup initial View
	 */
	public ClientView(Stage primaryStage, Observer observer) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("GroupFinderPanel");

		BorderPane mainWindowPane = new BorderPane();
		
		TopPane mainWindowTopPane = new TopPane(primaryStage, observer);
		mainWindowPane.setTop(mainWindowTopPane);

		//GridPane leftPane = new GridPane();

		Scene scene = new Scene(mainWindowPane, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	
	
}
