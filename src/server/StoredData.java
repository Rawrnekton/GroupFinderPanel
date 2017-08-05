package server;

import java.util.LinkedList;

import client.view.profilemanagingwindow.LoadedProfile;

/**
 * will be used to store the data the clients send it
 */
public class StoredData {

	private LinkedList<LoadedProfile> loadedProfileList = new LinkedList<LoadedProfile>();

	
	
	public StoredData() {

	}

	public LinkedList<LoadedProfile> getLoadedProfileList() {
		return loadedProfileList;
	}
	/**
	 * fügt der Profilliste ein neues Profil hinzu und löscht die ältere version, auch wenn diese nicht anders ist
	 * test auf identität scheint nicht nur aufwendiger sonern auch ineffizienter
	 * evtl profile erstelldatum angeben?
	 */
	public void addProfile(LoadedProfile loadedProfile) {
		for (int i = 0; i < loadedProfileList.size(); i++) {
			if(loadedProfileList.get(i).getClientID() == loadedProfile.getClientID()) {
				loadedProfileList.remove(i);
				break;
			}
		}

		loadedProfileList.add(loadedProfile);
	}
}
