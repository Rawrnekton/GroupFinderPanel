package client.view.manageprofilview;

import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ManageProfilView extends Observable {

	/*
	 * Misc Attributes
	 */
	LoadedProfile selectedProfile;

	/*
	 * Scene, Stages and Panes
	 */
	Scene scene;
	
	Stage profileManagementStage;
	BorderPane profileManagementStageMainPane;

	HBox profileManagementStageTopPane;
	GridPane profileManagementStageCenterPane;
	GridPane profileManagementStageBottomPane;
	/*
	 * Buttons, Labels and Textfields
	 */

	ObservableList<String> profileNames;
	ComboBox<String> selectedProfilComboBox;
	Button saveButton;
	Button applyProfileButton;
	Button deleteProfilButton;

	Label userNameLabel;
	TextField userNameTextField;
	Label serverAddressLabel;
	TextField serverAddressTextField;
	Label serverPasswordLabel;
	TextField serverPasswordTextField;
	Label newGameLabel;
	TextField newGameTextField;
	Button newGameButton;
	
	int listedGames;

	public ManageProfilView() {
		profileManagementStage = new Stage();
		profileManagementStageMainPane = new BorderPane();
		profileManagementStageTopPane = new HBox();
		profileManagementStageCenterPane = new GridPane();
		profileManagementStageBottomPane = new GridPane();

		scene = new Scene(profileManagementStageMainPane);
		
		selectedProfile = new LoadedProfile();
		listedGames = selectedProfile.getGameList().size();
		
		/*
		 * TopPane
		 */
		profileNames = FXCollections.observableArrayList("Profil 1", "Profil 2", "Profil 3");
		selectedProfilComboBox = new ComboBox<String>(profileNames);
		profileManagementStageTopPane.getChildren().add(selectedProfilComboBox);

		saveButton = new Button("Save Profile");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				userNameTextField.setText("Test2");
			}
		});
		profileManagementStageTopPane.getChildren().add(saveButton);

		applyProfileButton = new Button("Apply");
		profileManagementStageTopPane.getChildren().add(applyProfileButton);

		deleteProfilButton= new Button("Delete Profile");
		profileManagementStageTopPane.getChildren().add(deleteProfilButton);
		
		
		/*
		 * Center Pane, Label and Textfields
		 */
		userNameLabel = new Label("user name:");
		profileManagementStageCenterPane.add(userNameLabel, 0, 0);
		userNameTextField = new TextField();
		profileManagementStageCenterPane.add(userNameTextField, 1, 0, 2, 1);
		
		serverAddressLabel = new Label("server adress:");
		profileManagementStageCenterPane.add(serverAddressLabel, 0, 1);
		serverAddressTextField = new TextField("vigor-mortis.ddns.net");
		profileManagementStageCenterPane.add(serverAddressTextField, 1, 1, 2, 1);
		
		serverPasswordLabel = new Label("server password:");
		profileManagementStageCenterPane.add(serverPasswordLabel, 0, 2);
		serverPasswordTextField = new TextField();
		profileManagementStageCenterPane.add(serverPasswordTextField, 1, 2, 2, 1);
		
		newGameLabel = new Label("new game:");
		profileManagementStageCenterPane.add(newGameLabel, 0, 3);
		newGameTextField = new TextField();
		profileManagementStageCenterPane.add(newGameTextField, 1, 3);
		newGameButton = new Button("Add");
		profileManagementStageCenterPane.add(newGameButton, 2, 3);

		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(newGameTextField.getText().isEmpty()) return;
				
				for(String s : selectedProfile.getGameList()){
				  if(s.equals(newGameTextField.getText())) {
					  System.out.println(newGameTextField.getText() + " war schonmal da");
					  return;
				  }
				}
				
				selectedProfile.addGameToList(newGameTextField.getText());
				profileManagementStageBottomPane.getChildren().clear();
				
				for(int i = 0; i < selectedProfile.getGameList().size(); i++) {
					profileManagementStageBottomPane.add(new Label(selectedProfile.getGameList().get(i)), 0, i);
					Button deleteButton = new Button();
					//deleteButton.setAlignment(Pos.CENTER);
					deleteButton.setShape(new Circle(2));
					Image imageDelete = new Image(getClass().getResourceAsStream("imageDelete.png")); //needs some extra work //TODO
					deleteButton.setGraphic(new ImageView(imageDelete));
					deleteButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							selectedProfile.getGameList().remove(newGameTextField.getText());
						}
					});
					profileManagementStageBottomPane.add(deleteButton, 1, i);
				}
				
				profileManagementStage.sizeToScene();
			}
		});
		
		/*
		 * View Setup, almost done maybe redo the spacing and the actual width
		 * and height once i know how to correctly calculate them (if thats
		 * possible and feasible)
		 */
		profileManagementStageTopPane.setSpacing(5);
		profileManagementStageTopPane.setPadding(new Insets(5, 5, 5, 5));

		profileManagementStageCenterPane.setVgap(4);
		profileManagementStageCenterPane.setHgap(10);
		profileManagementStageCenterPane.setPadding(new Insets(5, 5, 5, 5));

		profileManagementStageBottomPane.setVgap(2);
		profileManagementStageBottomPane.setHgap(2);
		profileManagementStageBottomPane.setPadding(new Insets(5, 5, 5, 5));
		
		profileManagementStageMainPane.setTop(profileManagementStageTopPane);
		profileManagementStageMainPane.setCenter(profileManagementStageCenterPane);
		profileManagementStageMainPane.setBottom(profileManagementStageBottomPane);
		profileManagementStageMainPane.setPadding(new Insets(5, 5, 5, 5));

		//profileManagementStage.initStyle(StageStyle.UNDECORATED); //lul
		profileManagementStage.setTitle("Profile Manager");
		profileManagementStage.setScene(scene);
		profileManagementStage.show();
	}
}