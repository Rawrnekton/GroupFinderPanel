package client.controller.events.profilemanagement;

import client.view.ClientView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class NewGameBtnEvent implements EventHandler<ActionEvent>{

	ClientView clientView;
	
	public NewGameBtnEvent(ClientView clientView) {
		this.clientView = clientView;
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
