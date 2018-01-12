package client.view.mainwindow;

import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindowBase {
	private Stage primaryStage;
	
	private BorderPane mainWindowPane;
	private MainWindowTopPane mainWindowTopPane;

	public MainWindowBase(Stage primaryStage, Observer observer) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("GroupFinderPanel");
	
		mainWindowPane = new BorderPane();
		
		mainWindowTopPane = new MainWindowTopPane(); //this.primaryStage, observer);
		mainWindowPane.setTop(mainWindowTopPane);

		Scene scene = new Scene(mainWindowPane, 300, 250);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}
	
	public MainWindowTopPane getMainWindowTopPane() {
		return this.mainWindowTopPane;
	}
}