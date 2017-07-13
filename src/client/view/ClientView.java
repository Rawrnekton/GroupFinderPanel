package client.view;

import client.view.primaryview.TopPane;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * Sets up View for applicationa and manages the displayed content
 * that is supplied by the controller
 */
public class ClientView {
	Stage primaryStage;

	/*
	 * Setup initial View
	 */
	public void startUpView(Stage _primaryStage) {
		this.primaryStage = _primaryStage;
		primaryStage.setTitle("GroupFinderPanel");

		BorderPane mainWindowPane = new BorderPane();

		mainWindowPane.setTop(TopPane.setTopPane());

		GridPane leftPane = new GridPane();

		Scene scene = new Scene(mainWindowPane, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
