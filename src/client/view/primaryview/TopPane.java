package client.view.primaryview;

import java.util.Observer;

import client.view.manageprofilview.ProfilManageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TopPane extends HBox {

	Observer observer;

	public TopPane(Observer observer) {
		this.observer = observer;

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
				ProfilManageView manageProfilView = new ProfilManageView(getObserver());
			}
		});

		this.getChildren().add(btn1);
		this.getChildren().add(btn2);
		this.setSpacing(5);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	private Observer getObserver() {
		return this.observer;
	}
}
