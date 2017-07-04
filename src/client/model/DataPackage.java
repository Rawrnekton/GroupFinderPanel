package client.model;

import java.util.Date;
import java.util.LinkedList;

public class DataPackage {

	private String userName;
	private LinkedList<String> gameList;
	
	private int timeUntilReadySeconds;
	private Date lastManualRefresh;
	private Date timeUntilExpired;
	
	private String currentlyPlayedGame;
	private int inGameSinceSeconds;

	/*
	 * will use this if loading payload in tiny bits is prefered
	 */
	public DataPackage() {
		this.lastManualRefresh = new Date();
	}
	
	/*
	 * will use this if everything is already there and ready for takoff
	 */
	public DataPackage(String userName, LinkedList<String> gameList, 
			int timeUntilReadySeconds, Date lastManualRefresh, Date timeUntilExpired,
			String currentlyPlayedGame, int inGameSinceSeconds) {
		this.userName = userName;
		this.gameList = gameList; 
		this.timeUntilReadySeconds = timeUntilReadySeconds;
		this.lastManualRefresh = lastManualRefresh;
		this.timeUntilExpired = timeUntilExpired;
		this.currentlyPlayedGame = currentlyPlayedGame;
		this.inGameSinceSeconds = inGameSinceSeconds;
	}
	
	
	/*
	 * Set and Add Methods
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTimeUntilReady(int timeUntilReadySeconds) {
		this.timeUntilReadySeconds = timeUntilReadySeconds;
	}
	
	public void setGameList(LinkedList<String> gameList) {
		this.gameList = gameList;
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