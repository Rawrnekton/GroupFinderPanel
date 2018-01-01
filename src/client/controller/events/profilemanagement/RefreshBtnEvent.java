package client.controller.events.profilemanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class RefreshBtnEvent implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
			System.out.println("Hello World!");
	}
}
