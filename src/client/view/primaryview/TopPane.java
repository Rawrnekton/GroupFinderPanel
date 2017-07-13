package client.view.primaryview;

import client.view.manageprofilview.ManageProfilView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public abstract class TopPane {

	public static HBox setTopPane() {
		HBox topPane = new HBox(2);

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
				ManageProfilView manageProfilView = new ManageProfilView();
			}
		});

		topPane.getChildren().add(btn1);
		topPane.getChildren().add(btn2);
		return topPane;
	}
}
