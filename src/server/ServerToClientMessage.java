package server;

import java.io.Serializable;
import java.util.LinkedList;

import client.view.profilemanagingwindow.LoadedProfile;

/**
 * Simple Message Class that should be used once and then be discarded
 * Is used atm only to convey the loadedProfiles, but i will either
 * add more data that is transmitted to it, or come up with a way to
 * only ever send this and let the client figure out what he received
 * @author jonathan
 *
 */
public class ServerToClientMessage implements Serializable{

	/**
	 * to make it writable onto a socket 
	 */
	private static final long serialVersionUID = -4555583242112908918L;
	
	private int clientID;
	private LinkedList<LoadedProfile> loadedProfileList;

	public ServerToClientMessage() {
		loadedProfileList = new LinkedList<LoadedProfile>();
	}
	
	/* ---------- GET-METHODS ---------- */
	public LinkedList<LoadedProfile> getLoadedProfileList() {
		return this.loadedProfileList;
	}
	public int getNextClientID() {
		return this.clientID;
	}
	
	/* ---------- SET-METHODS ---------- */
	public void setLoadedProfileList(LinkedList<LoadedProfile> loadedProfileList) {
		this.loadedProfileList = loadedProfileList;
	}
	
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
}