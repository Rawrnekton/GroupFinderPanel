package client.view.manageprofilview;

import java.util.Observable;

import client.model.Lib;
import client.model.LoadedProfile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageProfilView extends Observable{

	LoadedProfile selectedProfile;

	TextField newLabel;
	
	public ManageProfilView() {
		Stage managementStage = new Stage();
		BorderPane managementStageMainPane = new BorderPane();

		HBox manageProfilViewTopPane = new HBox();
		VBox manageProfilViewCenterPane = new VBox();
		
		newLabel = new TextField();
		newLabel.setText("Test");
		
		
		
		Button loadButton = new Button();
		loadButton.setText("Load Profile");
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedProfile = Lib.createProfileFromFile(null); //TODO

			}
		});
		manageProfilViewTopPane.getChildren().add(loadButton);
		
		
		Button saveButton = new Button();
		saveButton.setText("Save Profile");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				newLabel.setText("Test2");
			}
		});
		manageProfilViewTopPane.getChildren().add(saveButton);
		
		Button applyProfileButton = new Button();
		applyProfileButton.setText("Apply");
		manageProfilViewTopPane.getChildren().add(applyProfileButton);
		
		
		
		
		
		manageProfilViewCenterPane.getChildren().add(newLabel);
		
		
		
		managementStageMainPane.setTop(manageProfilViewTopPane);
		managementStageMainPane.setCenter(manageProfilViewCenterPane);
		
		Scene scene = new Scene(managementStageMainPane, 250, 250);
		managementStage.setScene(scene);
		managementStage.show();

	}
	
	
	
}
