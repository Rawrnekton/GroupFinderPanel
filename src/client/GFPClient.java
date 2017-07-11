package client;

import client.view.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

public class GFPClient extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientView testView = new ClientView();
		testView.startUpView(primaryStage);
	}
}