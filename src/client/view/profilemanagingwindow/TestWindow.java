package client.view.profilemanagingwindow;

import java.util.Observable;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TestWindow extends Observable {
	LoadedProfile loadedProfile;
	Stage primaryStage;
	
	Scene scene;
	Stage profileManagementStage;
	BorderPane profileManagementStageMainPane;
	
	public TestWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		profileManagementStage = new Stage();
		profileManagementStageMainPane = new BorderPane();
//		profileManagementStageTopPane = new HBox();
//		profileManagementStageCenterPane = new GridPane();
//		profileManagementStageBottomPane = new GridPane();

		scene = new Scene(profileManagementStageMainPane);

		
		
		profileManagementStage.initModality(Modality.WINDOW_MODAL);
		profileManagementStage.initOwner(primaryStage);
		profileManagementStage.setTitle("Profile Manager");
		profileManagementStage.setScene(scene);
		profileManagementStage.show();
	}
	
	
}
