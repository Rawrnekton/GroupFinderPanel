package server;

import java.io.Serializable;
import java.util.LinkedList;

import client.view.profilemanagingwindow.LoadedProfile;

public class ServerToClientMessage implements Serializable{

	/**
	 * to make it writable onto a socket 
	 */
	private static final long serialVersionUID = -4555583242112908918L;
	private int nextClientID;
	private LinkedList<LoadedProfile> loadedProfileList;

	public ServerToClientMessage() {
		loadedProfileList = new LinkedList<LoadedProfile>();
		nextClientID = 0;
	}
	
	public LinkedList<LoadedProfile> getLoadedProfileList() {
		return this.loadedProfileList;
	}
	public int getNextClientID() {
		return this.nextClientID;
	}
	
	public void setLoadedProfileList(LinkedList<LoadedProfile> loadedProfileList) {
		this.loadedProfileList = loadedProfileList;
	}
	public void setNextClientID(int nextClientID) {
		this.nextClientID = nextClientID;
	}
	
}