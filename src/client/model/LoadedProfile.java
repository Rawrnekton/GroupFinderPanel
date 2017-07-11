package client.model;

import java.util.LinkedList;

public class LoadedProfile {

	private String profilName;
	private String userName;
	
	private LinkedList<String> gameList;
	
	public void setProfilName(String profilName) {
		this.profilName = profilName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setGameList(LinkedList<String> gameList) {
		this.gameList = gameList;
	}
	
	public String getProfilName() {
		return profilName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public LinkedList<String> getGameList() {
		return gameList;
	}
}
