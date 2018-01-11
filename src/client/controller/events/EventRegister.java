package client.controller.events;

import client.controller.events.mainwindow.ManageProfileBtnEvent;
import client.controller.events.profilemanagement.ApplyProfileButtonEvent;
import client.controller.events.profilemanagement.NewGameBtnEvent;
import client.controller.events.profilemanagement.RefreshBtnEvent;
import client.controller.events.profilemanagement.SaveBtnEvent;
import client.view.ClientView;
import client.view.mainwindow.MainWindowTopPane;
import client.view.profilemanagingwindow.ProfileManagingWindowBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventRegister {
	private ClientView clientView;

	/* ----- MainWindow ----- */
	private EventHandler<ActionEvent> manageProfileBtnEvent;
	
	/* ----- ProfileManagingWindow ----- */
	private EventHandler<ActionEvent> newGameBtnEvent;
//	private EventHandler<ActionEvent> refreshBtnEvent;
	private EventHandler<ActionEvent> saveBtnEvent;
	private EventHandler<ActionEvent> applyProfileBtnEvent;
	
	public EventRegister(ClientView clientView) {
		this.clientView = clientView;

		/* Creation of all Events currently in use by the whole application 
		 * //TODO revisiting to create a lazy load? to reduce memory hog
		 */
//		Debug.debug(this, "Creating the events.");
		
		/* ----- MainWindow ----- */	
		manageProfileBtnEvent = new ManageProfileBtnEvent(clientView.getPrimaryStage());
		
		/* ----- ProfileManagingWindow ----- */
		newGameBtnEvent = new NewGameBtnEvent(this.clientView);
//		refreshBtnEvent = new RefreshBtnEvent();
		saveBtnEvent = new SaveBtnEvent();
		applyProfileBtnEvent = new ApplyProfileButtonEvent();
	}
	
	public void registerMainWindowEvents() {
		MainWindowTopPane mainWindowTopPane = clientView.getMainWindowBase().getMainWindowTopPane();
		mainWindowTopPane.setManageProfileBtnAction(manageProfileBtnEvent);
	}
	
	public void registerProfileManagementWindowEvents() {
		ProfileManagingWindowBase profileManagingWindowBase = clientView.getProfileManagingWindowBase();
		profileManagingWindowBase.setNewGameBtnAction(newGameBtnEvent);
		//refresh button event? should not be bound to a button i suppose
		profileManagingWindowBase.setSaveBtn(saveBtnEvent);
		profileManagingWindowBase.setApplyProfileBtn(applyProfileBtnEvent);
	}
}
