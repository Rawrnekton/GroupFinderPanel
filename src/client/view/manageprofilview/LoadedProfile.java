package client.view.manageprofilview;

import java.util.LinkedList;

public class LoadedProfile {

	private String profilName;
	private String userName;
	
	private String serverAddress;
	private String serverPassword;
	
	private LinkedList<String> gameList;
	
	public LoadedProfile() {
		gameList= new LinkedList<String>();
	}
		
	public void setProfilName(String profilName) {
		this.profilName = profilName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setServerAdress(String serverAdress) {
		this.serverAddress = serverAdress;
	}
	
	public void serverPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}
	
	public void addGameToList(String newGame) {
		gameList.add(newGame);
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
	
	public String getServerAdress() {
		return this.serverAddress;
	}
	
	public String getServerPassword() {
		return this.serverPassword;
	}
	
	public LinkedList<String> getGameList() {
		return gameList;
	}
}
