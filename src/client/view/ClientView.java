package client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/*
 * Sets up View for applicationa and manages the displayed content
 * that is supplied by the controller
 */
public class ClientView{
	Stage primaryStage;
	
	/*
	 * Setup initial View
	 */
	public void startUpView(Stage _primaryStage) {
		this.primaryStage = _primaryStage;
		primaryStage.setTitle("GroupFinderPanel");		

		BorderPane mainWindowPane = new BorderPane();

		mainWindowPane.setTop(setTopPane());
		
		
		GridPane leftPane = new GridPane();

		Scene scene = new Scene(mainWindowPane, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private GridPane setTopPane() {
		GridPane topPane = new GridPane();
		Button btn1 = new Button();
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
		topPane.add(btn1, 0, 0);
		return topPane;
	}
	
}
