package client.controller.events.profilemanagement;

import client.view.profilemanagingwindow.LoadedProfile;
import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SaveBtnEvent implements EventHandler<ActionEvent> {

	public SaveBtnEvent() {
		
	}

	@Override
	public void handle(ActionEvent event) {
			ProfileManagingWindowBase profileManagingWindowBase = (ProfileManagingWindowBase) event.getSource();
			LoadedProfile loadedProfile = profileManagingWindowBase.getLoadedProfile();
			
			loadedProfile.setProfilName(profileManagingWindowBase.getComboBoxContent());
			loadedProfile.setUserName(profileManagingWindowBase.getUserNameTextFieldConten());
			loadedProfile.setServerAdress(profileManagingWindowBase.getServerAddressTextFieldContent());
			loadedProfile.setGroupName(profileManagingWindowBase.getGroupNameTextFieldContent());
			loadedProfile.setServerPassword(profileManagingWindowBase.getServerPasswordTextFieldContent());
			
			profileManagingWindowBase.getLoadedProfile().saveToFile();;

			profileManagingWindowBase.updateComboBoxContent();		
	}
}
