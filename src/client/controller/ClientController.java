package client.controller;

import java.util.Observable;
import java.util.Observer;

import client.controller.events.EventRegister;
import client.model.ClientModel;
import client.view.ClientView;
import lib.Debug;

public class ClientController implements Observer{
	
	private ClientView clientView;
	private ClientModel clientModel;
	private EventRegister eventRegister;
	
	public ClientController(ClientView clientView, ClientModel clientModel) {
		Debug.debug(this, "Creating the clientController.");
		this.clientView = clientView;
		this.clientModel = clientModel;
		this.eventRegister = new EventRegister(this.clientView);
		
		Debug.debug(this, "Registering events to the buttons.");
		eventRegister.registerMainWindowEvents();
	}
	
	public void registerEventsMain() {
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
