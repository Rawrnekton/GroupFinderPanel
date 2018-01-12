package client.view.mainwindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MainWindowTopPane extends HBox {

//	private Stage primaryStage;
	
	private Button refreshBtn;
	private Button manageProfileBtn;

	public MainWindowTopPane() { //Stage primaryStage, Observer observer) {
//		this.primaryStage = primaryStage;
		
		refreshBtn = new Button();
		refreshBtn.setText("Refresh");

		manageProfileBtn = new Button();
		manageProfileBtn.setText("Manage Profiles");

		this.getChildren().add(refreshBtn);
		this.getChildren().add(manageProfileBtn);
		this.setSpacing(5);
		this.setPadding(new Insets(5, 5, 5, 5));
	}

	/* ---------- EventHandle-Setter ---------- */
	
	public void setRefreshBtnAction(EventHandler<ActionEvent> eventHandler) {
		this.refreshBtn.setOnAction(eventHandler);
	}
	
	public void setManageProfileBtnAction(EventHandler<ActionEvent> eventHandler) {
		this.manageProfileBtn.setOnAction(eventHandler);
	}
}
