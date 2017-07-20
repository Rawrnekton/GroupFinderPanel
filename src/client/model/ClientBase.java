package client.model;

import java.util.concurrent.FutureTask;

import client.view.manageProfileView.LoadedProfile;

public class ClientBase {

	private Object clientMessage;
	
	public static void main(String args[]) {
		
		try {
			@SuppressWarnings("unused")
			ClientBase ksClient;
			ksClient = new ClientBase(new LoadedProfile("Profil 1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ClientBase(Object clientProfile) throws Exception {
		this.clientMessage = clientProfile;
		this.worker();
	}
	
	private void worker() throws Exception {
		ClientHandler ch = new ClientHandler(clientMessage);
		FutureTask<Object> ft = new FutureTask<Object>(ch);
		Thread tft = new Thread(ft);
		tft.start();
		
		while(!ft.isDone()) {
			Thread.yield();
		}
		
		//ft.get() will return the server message object
		System.out.println("Server Antwort: " + ft.get());
	}
}