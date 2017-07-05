package client.model;

import java.util.Date;
import java.util.LinkedList;

public class MessageClientToServer {

	private String userName;
	private LinkedList<String> gameList;
	
	private int timeUntilReadySeconds;
	private Date lastManualRefresh;
	private Date timeUntilExpired;
	
	/*
	 * this is for later use
	private String currentlyPlayedGame;
	private int inGameSinceSeconds;
	*/
	
	public MessageClientToServer() {
		this.lastManualRefresh = new Date();
	}
	
	public MessageClientToServer(String userName, LinkedList<String> gameList, 
			int timeUntilReadySeconds, Date lastManualRefresh, Date timeUntilExpired) {
		this.userName = userName;
		this.gameList = gameList; 
		this.timeUntilReadySeconds = timeUntilReadySeconds;
		this.lastManualRefresh = lastManualRefresh;
		this.timeUntilExpired = timeUntilExpired;
	}
	/*
	 * Set and Add Methods
	 */
	public void setPlayerName(String UserName) {
		this.userName = UserName;
	}

	public void setTimeUntilReady(int timeUntilReadySeconds) {
		this.timeUntilReadySeconds = timeUntilReadySeconds;
	}

	public void addGame(String newGame) {
		gameList.add(newGame);
	}
	
	public void updateDate() {
		this.lastManualRefresh = new Date();
	}
	
	/*
	 * Get-Methoden
	 */
	
}
