package client;

import java.util.LinkedList;

import client.model.ClientModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GFPClient extends Application{

	LinkedList<Button> btnList;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("GroupFinderPanel");
		
		Button btn1 = new Button();
		btn1.setText("Refresh");
		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//System.out.println("Hello World!");
				
				try {
					@SuppressWarnings("unused")
					ClientModel test = new ClientModel("FunkyTown");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//setBtn("Funky");
			}
		});
		//btnList.add(btn1);
		
		int r = 30;
		Button btn2 = new Button();
		//btn2.setShape(new Circle(r));
		btn2.setText("New Game");
		/*btn2.setMinSize(2 * r, 2 * r);
		btn2.setMaxSize(2 * r, 2 * r);
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			int index = 3;
			int radius = 30;
			@Override
			public void handle(ActionEvent event) {
				try {
					btnList.add(new Button());
					btnList.get(index).setShape(new Circle(radius));
					btnList.get(index).setText(String.valueOf(index));
					btnList.get(index).setMinSize(2 * radius, 2 * radius);
					btnList.get(index).setMaxSize(2 * radius, 2 * radius);
					index++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})*/;
		//btnList.add(btn2);
		

		System.out.println("did it crash?");
		
		BorderPane borderPane = new BorderPane();
				
		borderPane.setTop(btn1);
		borderPane.setTop(btn2);
		
		
		
		
		Scene scene = new Scene(borderPane, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void setBtn(String _text) {
		/*try {
			btnList.get(0).setText("Funky");
		} catch (Exception e) {
			/*
			 * The only thing necessary for the triumph of evil is for good men to do nothing.
			 * Just like this bit here.
			 *
		}*/
	}
}