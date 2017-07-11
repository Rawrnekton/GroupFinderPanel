package client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
				Stage managementStage = new Stage();
				BorderPane managementStageMainPane = new BorderPane();

				Scene scene = new Scene(managementStageMainPane, 250, 250);
				managementStage.setScene(scene);
				managementStage.show();
			}
		});

		topPane.getChildren().add(btn1);
		topPane.getChildren().add(btn2);
		return topPane;
	}
}
