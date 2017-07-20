package client.view.primaryView;

import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrimaryStageView {
	Stage primaryStage;

	public PrimaryStageView(Stage primaryStage, Observer observer) {
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