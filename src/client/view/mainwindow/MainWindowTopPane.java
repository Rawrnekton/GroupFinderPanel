package client.view.mainwindow;

import java.util.Observer;

import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainWindowTopPane extends HBox {

	Observer observer;
	Stage primaryStage;

	public MainWindowTopPane(Stage primaryStage, Observer observer) {
		this.observer = observer;
		this.primaryStage = primaryStage;
		/*
		 * create button setText setOnAction setAttributes?
		 */
		Button btn1 = new Button();
		btn1.setText("Refresh");
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		Button btn2 = new Button();
		btn2.setText("Manage Profiles");
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				@SuppressWarnings("unused")
				ProfileManagingWindowBase manageProfilView = new ProfileManagingWindowBase(getPrimaryStage(), getObserver());
			}
		});

		this.getChildren().add(btn1);
		this.getChildren().add(btn2);
		this.setSpacing(5);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	/*
	 * dont understand why i cant just use the variables, since they are defined
	 * lcally .. but eh, works i guess
	 */
	private Observer getObserver() {
		return this.observer;
	}

	private Stage getPrimaryStage() {
		return this.primaryStage;
	}
}
