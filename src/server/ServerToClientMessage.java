package server;

import java.io.Serializable;
import java.util.LinkedList;

import client.view.profilemanagingwindow.LoadedProfile;

/**
 * Simple Message Class that should be used once and then be discarded
 * @author jonathan
 *
 */
public class ServerToClientMessage implements Serializable{

	/**
	 * to make it writable onto a socket 
	 */
	private static final long serialVersionUID = -4555583242112908918L;
	
	private int nextClientID;
	private LinkedList<LoadedProfile> loadedProfileList;

	public ServerToClientMessage(int nextClientID) {
		loadedProfileList = new LinkedList<LoadedProfile>();
		this.nextClientID = nextClientID;
	}
	
	/* ---------- SET-METHODS ---------- */
	public LinkedList<LoadedProfile> getLoadedProfileList() {
		return this.loadedProfileList;
	}
	public int getNextClientID() {
		return this.nextClientID;
	}
	
	/* ---------- GET-METHODS ---------- */
	public void setLoadedProfileList(LinkedList<LoadedProfile> loadedProfileList) {
		this.loadedProfileList = loadedProfileList;
	}
}