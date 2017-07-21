package client.view.profilemanagingwindow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedList;

import client.model.Lib;

public class LoadedProfile implements Serializable{

	private static final long serialVersionUID = 4061867404946651656L;
	private String profilName;
	private String userName;
	private int clientID;
	
	private String groupName;
	private String serverAddress;
	private String serverPassword;
	
	private LinkedList<String> usedGameList;
	private LinkedList<String> unusedGameList;
	
	public LoadedProfile(String usedProfil) {
		this.profilName = usedProfil;
		loadProfile(usedProfil);
		this.clientID = 0;
	}
		
	public void setProfilName(String profilName) {
		this.profilName = profilName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}	
	public void setclientID(int clientID) {
		this.clientID = clientID;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setServerAdress(String serverAdress) {
		this.serverAddress = serverAdress;
	}
	public void setServerPassword(String serverPassword) {
		this.serverPassword = serverPassword;
	}
	public void setUsedGameList(LinkedList<String> usedGameList) {
		this.usedGameList = usedGameList;
	}
	public void setUnusedGameList(LinkedList<String> unusedGameList) {
		this.unusedGameList = unusedGameList;
	}
	
	public void addGameToList(String newGame) {
		usedGameList.add(newGame);
	}
	
	public String getProfileName() {
		return profilName;
	}
	public String getUserName() {
		return userName;
	}
	public int getClientID() {
		return this.clientID;
	}
	public String getGroupName(){
		return groupName;
	}
	public String getServerAdress() {
		return this.serverAddress;
	}
	public String getServerPassword() {
		return this.serverPassword;
	}
	public LinkedList<String> getUsedGameList() {
		return usedGameList;
	}
	public LinkedList<String> getUnusedGameList() {
		return unusedGameList;
	}
	
	public void loadProfile(String usedProfil) {
		usedGameList = new LinkedList<String>();
		unusedGameList = new LinkedList<String>();
		
		final String FILENAME = Lib.PROFILEPATH + usedProfil;
		
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			br = new BufferedReader(new FileReader(FILENAME));

			profilName = br.readLine();
			userName = br.readLine();
			serverAddress = br.readLine();
			groupName = br.readLine();
			serverPassword = br.readLine();
			
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				if(sCurrentLine.contains("!")) {
					unusedGameList.add(sCurrentLine.replace("!", ""));
				} else {
					usedGameList.add(sCurrentLine);
				}
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}
	
	public void saveToFile(String toBeSaved) {
		final String FILENAME = Lib.PROFILEPATH + profilName;
		FileWriter write;
		try {
			write = new FileWriter(FILENAME, false);

			PrintWriter pw = new PrintWriter(write);
			
			pw.printf( "%s" + "%n", toBeSaved);
			pw.close();
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File could not be saved!");
		}
		
	}
	
	@Override
	public String toString() {
		String re = "";
		
		re += profilName + "\n";
		re += userName + "\n";
		re += serverAddress + "\n";
		re += groupName + "\n";
		re += serverPassword + "\n";
		
		int index = 0;
		
		while(index < usedGameList.size()) {
			re += usedGameList.get(index) + "\n";
			index++;
		}
		
		index = 0;
		while(index < unusedGameList.size()) {
			re += unusedGameList.get(index) + "!\n";
			index++;
		}
		
		re = re.substring(0, re.length() - 1);
		
		return re;
	}
}
