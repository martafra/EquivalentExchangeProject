package logic.controller.graphic;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.LoginController;
import logic.entity.ChatMessage;
import logic.support.interfaces.Observer;
import logic.support.other.Bundle;
import logic.support.other.MailBox;
import logic.support.other.SceneManageable;
import logic.support.other.Subject;

public class LoginProva extends SceneManageable implements Observer{
	
	LoginController controller = new LoginController();
	private MailBox mailbox;
	@FXML
	private Label textview;

	private Boolean isObserving = false;

	
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		LoginBean login = (LoginBean) this.bundle.getBean("loggedUser");
		mailbox = (MailBox) getBundle().getObject("mailbox");
		registerTo(mailbox);
		UserBean loggedUser = controller.getUserByLoginData(login);
		
		textview.setText(loggedUser.getUserID() +"\n\n"+ loggedUser.getName() + " " + loggedUser.getLastName() + "\n" + loggedUser.getEmail());
	}


	@Override
	public void update() {
		
		var messages = (ArrayList<ChatMessage>) mailbox.getState();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				textview.setText(messages.get(messages.size()-1).getText());
			}});
		
	}

	private void registerTo(Subject sub){
		if(!isObserving){
			sub.register(this);
			isObserving = true;
		}	
	}
    
}
