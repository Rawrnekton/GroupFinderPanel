package client.view.profilemanagingwindow;

import java.util.Observable;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestWindow extends Observable {
	LoadedProfile loadedProfile;
	Stage primaryStage;
	
	Scene scene;
	Stage profileManagementStage;
	BorderPane profileManagementStageMainPane;
	
	public static void main(String[] args) {
		TestWindow test = new TestWindow(new Stage());
	}
	
	public TestWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		profileManagementStage = new Stage();
		profileManagementStageMainPane = new BorderPane();
//		profileManagementStageTopPane = new HBox();
//		profileManagementStageCenterPane = new GridPane();
//		profileManagementStageBottomPane = new GridPane();

		scene = new Scene(profileManagementStageMainPane);

	}
	
	
}
