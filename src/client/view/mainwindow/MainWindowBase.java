package client.view.mainwindow;

import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindowBase {
	Stage primaryStage;

	public MainWindowBase(Stage primaryStage, Observer observer) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("GroupFinderPanel");
	
		BorderPane mainWindowPane = new BorderPane();
		
		MainWindowTopPane mainWindowTopPane = new MainWindowTopPane(primaryStage, observer);
		mainWindowPane.setTop(mainWindowTopPane);
	
		//GridPane leftPane = new GridPane();
	
		Scene scene = new Scene(mainWindowPane, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}