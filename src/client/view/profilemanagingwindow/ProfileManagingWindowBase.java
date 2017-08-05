package client.view.profilemanagingwindow;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import client.model.Lib;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProfileManagingWindowBase extends Observable {

	/*
	 * Misc Attributes
	 */
	LoadedProfile selectedProfile;
	Observer observer;
	Stage primaryStage;

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
	ComboBox<String> selectedProfileComboBox;
	String[] listOfProfiles;
	Button saveButton;
	Button applyProfileButton;
	Button deleteProfileButton;

	Label userNameLabel;
	TextField userNameTextField;
	Label serverAddressLabel;
	TextField serverAddressTextField;
	Label serverPasswordLabel;
	TextField serverPasswordTextField;
	Label groupNameLabel;
	TextField groupNameTextField;
	Label newGameLabel;
	TextField newGameTextField;
	Button newGameButton;

	public ProfileManagingWindowBase(Stage primaryStage, Observer observer) {
		this.observer = observer;
		this.primaryStage = primaryStage;
		addObserver(observer);

		profileManagementStage = new Stage();
		profileManagementStageMainPane = new BorderPane();
		profileManagementStageTopPane = new HBox();
		profileManagementStageCenterPane = new GridPane();
		profileManagementStageBottomPane = new GridPane();

		scene = new Scene(profileManagementStageMainPane);

		selectedProfileComboBox = new ComboBox<String>();
		selectedProfileComboBox.setPromptText("Unbench the Kench");
		selectedProfileComboBox.setEditable(true);
		selectedProfileComboBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedProfile = new LoadedProfile(selectedProfileComboBox.getValue());
				setTextFields();
				setBottomPane();
			}
		});
		profileManagementStageTopPane.getChildren().add(selectedProfileComboBox);
		updateComboBoxContent();
		selectedProfileComboBox.getSelectionModel().selectFirst();
		if (selectedProfileComboBox.getValue() == null) {
			selectedProfile = new LoadedProfile("profilTemplate");
		} else {
			selectedProfile = new LoadedProfile(selectedProfileComboBox.getValue());
		}

		saveButton = new Button("Save Profile");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				selectedProfile.setProfilName(selectedProfileComboBox.getEditor().getText());
				selectedProfile.setUserName(userNameTextField.getText());
				selectedProfile.setServerAdress(serverAddressTextField.getText());
				selectedProfile.setGroupName(groupNameTextField.getText());
				selectedProfile.setServerPassword(serverPasswordTextField.getText());

				selectedProfile.saveToFile(selectedProfile.toString());

				updateComboBoxContent();
			}
		});
		profileManagementStageTopPane.getChildren().add(saveButton);

		applyProfileButton = new Button("Apply");
		applyProfileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedProfile.setProfilName(selectedProfileComboBox.getEditor().getText());
				selectedProfile.setUserName(userNameTextField.getText());
				selectedProfile.setServerAdress(serverAddressTextField.getText());
				selectedProfile.setGroupName(groupNameTextField.getText());
				selectedProfile.setServerPassword(serverPasswordTextField.getText());

				setChanged();
				notifyObservers(selectedProfile);
			}
		});
		profileManagementStageTopPane.getChildren().add(applyProfileButton);

		deleteProfileButton = new Button("Delete Profile");
		deleteProfileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File toBeDeleted = new File(Lib.PROFILEPATH + selectedProfileComboBox.getValue());
				selectedProfileComboBox.getSelectionModel().selectFirst();
				toBeDeleted.delete();
				
				updateComboBoxContent();
			}
		});
		profileManagementStageTopPane.getChildren().add(deleteProfileButton);

		/*
		 * Center Pane, Label and Textfields
		 */
		userNameLabel = new Label("user name:");
		profileManagementStageCenterPane.add(userNameLabel, 0, 0);
		userNameTextField = new TextField();
		profileManagementStageCenterPane.add(userNameTextField, 1, 0, 2, 1);

		serverAddressLabel = new Label("server adress:");
		profileManagementStageCenterPane.add(serverAddressLabel, 0, 1);
		serverAddressTextField = new TextField();
		profileManagementStageCenterPane.add(serverAddressTextField, 1, 1, 2, 1);

		groupNameLabel = new Label("group name:");
		profileManagementStageCenterPane.add(groupNameLabel, 0, 2);
		groupNameTextField = new TextField();
		profileManagementStageCenterPane.add(groupNameTextField, 1, 2, 2, 1);

		serverPasswordLabel = new Label("server password:");
		profileManagementStageCenterPane.add(serverPasswordLabel, 0, 3);
		serverPasswordTextField = new TextField();
		profileManagementStageCenterPane.add(serverPasswordTextField, 1, 3, 2, 1);

		newGameLabel = new Label("new game:");
		profileManagementStageCenterPane.add(newGameLabel, 0, 4);
		newGameTextField = new TextField();
		profileManagementStageCenterPane.add(newGameTextField, 1, 4);
		newGameButton = new Button("Add");
		profileManagementStageCenterPane.add(newGameButton, 2, 4);

		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (newGameTextField.getText().isEmpty())
					return;
				for (String s : selectedProfile.getUsedGameList())
					if (s.equals(newGameTextField.getText()))
						return;

				selectedProfile.addGameToList(newGameTextField.getText());
				setBottomPane();
			}
		});

		setTextFields();
		setBottomPane();

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
		profileManagementStage.initModality(Modality.WINDOW_MODAL);
		profileManagementStage.initOwner(primaryStage);
		profileManagementStage.setTitle("Profile Manager");
		profileManagementStage.setScene(scene);
		profileManagementStage.show();
	}

	private void setTextFields() {
		userNameTextField.setText(selectedProfile.getUserName());
		serverAddressTextField.setText(selectedProfile.getServerAdress());
		serverPasswordTextField.setText(selectedProfile.getServerPassword());
		groupNameTextField.setText(selectedProfile.getGroupName());
	}

	private void setBottomPane() {
		profileManagementStageBottomPane.getChildren().clear();

		Collections.sort(selectedProfile.getUsedGameList());
		Collections.sort(selectedProfile.getUnusedGameList());
		//selectedProfile.getUsedGameList()

		int maxColoumCount = 4;
		int currentColoum = 0;
		int currentRow = 0;
		int buttonWidth = 120;
		int buttonHeight = 30;

		for (int i = 0; i < selectedProfile.getUsedGameList().size(); i++) {
			Button deleteButton = new Button(selectedProfile.getUsedGameList().get(i));
			deleteButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					Button button = null;

					Object o = arg0.getSource();
					if (o instanceof Button)
						button = (Button) o;

					String toBeRemoved = button.getText();

					for (String s : selectedProfile.getUsedGameList())
						if (s.equals(toBeRemoved)) {
							selectedProfile.getUsedGameList().remove(s);
							selectedProfile.getUnusedGameList().add(s);
							break;
						}

					setBottomPane();
					profileManagementStage.sizeToScene();
				}
			});
			deleteButton.setMaxWidth(buttonWidth);
			deleteButton.setMinWidth(buttonWidth);
			deleteButton.setMaxHeight(buttonHeight);
			deleteButton.setMinHeight(buttonHeight);
			deleteButton.setAlignment(Pos.CENTER);
			if (deleteButton.getText().length() < 14)
				deleteButton.setStyle("-fx-base: #000000;"); //-fx-font: 22 arial; um Textart zu ändern
			else
				deleteButton.setStyle("-fx-base: #000000; -fx-font: 10 arial;");

			profileManagementStageBottomPane.add(deleteButton, currentColoum, currentRow);
			currentColoum = (currentColoum < (maxColoumCount - 1)) ? currentColoum += 1 : 0; //spicy
			if (currentColoum == 0)
				currentRow++;
		}

		currentRow++;
		currentColoum = 0;

		for (int i = 0; i < selectedProfile.getUnusedGameList().size(); i++) {
			Button deleteButton = new Button(selectedProfile.getUnusedGameList().get(i));
			deleteButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					Button button = null;

					Object o = arg0.getSource();
					if (o instanceof Button)
						button = (Button) o;

					String toBeRemoved = button.getText();

					for (String s : selectedProfile.getUnusedGameList())
						if (s.equals(toBeRemoved)) {
							selectedProfile.getUnusedGameList().remove(s);
							selectedProfile.getUsedGameList().add(s);
							break;
						}

					profileManagementStageBottomPane.getChildren().remove(button);
					setBottomPane();
					profileManagementStage.sizeToScene();
				}
			});
			deleteButton.setMaxWidth(buttonWidth);
			deleteButton.setMinWidth(buttonWidth);
			deleteButton.setMaxHeight(buttonHeight);
			deleteButton.setMinHeight(buttonHeight);
			deleteButton.setAlignment(Pos.CENTER);
			if (deleteButton.getText().length() < 14)
				deleteButton.setStyle("-fx-base: #b6e7c9;"); //-fx-font: 22 arial; um Textart zu ändern
			else
				deleteButton.setStyle("-fx-base: #b6e7c9; -fx-font: 10 arial;");

			profileManagementStageBottomPane.add(deleteButton, currentColoum, currentRow);

			currentColoum = (currentColoum < (maxColoumCount - 1)) ? currentColoum += 1 : 0; //spicy
			if (currentColoum == 0)
				currentRow++;
		}
		profileManagementStage.sizeToScene();
	}

	private void updateComboBoxContent() {
		File folder = new File(Lib.PROFILEPATH);
		File[] listOfProfileFiles = folder.listFiles();
		listOfProfiles = new String[listOfProfileFiles.length];
		for (int i = 0; i < listOfProfileFiles.length; i++) {
			if (listOfProfileFiles[i].isFile()) {
				//System.out.println("File " + listOfProfileFiles[i].getName());
				listOfProfiles[i] = listOfProfileFiles[i].getName();
			} else if (listOfProfileFiles[i].isDirectory()) {
				//TODO should be changed to actually prevent it from crashing if a directory is found here
				//System.out.println("Directory " + listOfProfileFiles[i].getName());
			}
		}
		Arrays.sort(listOfProfiles);
		profileNames = FXCollections.observableArrayList(listOfProfiles);
		
		selectedProfileComboBox.setItems(profileNames);
	}
}