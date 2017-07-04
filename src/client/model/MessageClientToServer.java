package client.model;

import java.util.Date;
import java.util.LinkedList;

public class MessageClientToServer {

	private String UserName;
	private LinkedList<String> games;
	
	private int timeUntilReadySeconds;
	private Date lastManualRefresh;
	private Date timeUntilExpired;
	
	private String currentPlayedGame;
	private int inGameSinceSeconds;

	
	public MessageClientToServer() {
		this.lastManualRefresh = new Date();
	}
	
	
	/*
	 * Set and Add Methods
	 */
	public void setPlayerName(String UserName) {
		this.UserName = UserName;
	}

	public void setTimeUntilReady(int timeUntilReadySeconds) {
		this.timeUntilReadySeconds = timeUntilReadySeconds;
	}

	public void addGame(String newGame) {
		games.add(newGame);
	}
	
	public void updateDate() {
		this.lastManualRefresh = new Date();
	}
	
	/*
	 * Get-Methoden
	 */
	
	
}
