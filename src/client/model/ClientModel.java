package client.model;

import java.util.concurrent.FutureTask;

import client.view.profilemanagingwindow.LoadedProfile;

public class ClientModel {

	private Object clientMessage;
	private LoadedProfile loadedProfile;
	
	
//	public static void main(String args[]) {
//		
//		try {
//			ClientModel clientBase = new ClientModel(new LoadedProfile("Profil 1"));
//			clientBase.worker();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public ClientModel(Object clientProfile) throws Exception {
		this.clientMessage = clientProfile;
	}
	
//	private void worker() throws Exception {
//		ClientHandler ch = new ClientHandler(clientMessage);
//		FutureTask<Object> ft = new FutureTask<Object>(ch);
//		Thread tft = new Thread(ft);
//		tft.start();
//		
//		while(!ft.isDone()) {
//			Thread.yield();
//		}
//		
//		//ft.get() will return the server message object
//		System.out.println("Server Antwort: " + ft.get());
//	}
//	
//	public LoadedProfile getLoadedProfile() {
//		return this.loadedProfile;
//	}
}