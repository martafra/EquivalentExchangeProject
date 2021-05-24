package logic.controller.graphic;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import logic.bean.LoginBean;
import logic.controller.application.ChatController;
import logic.support.interfaces.Observer;
import logic.support.other.HeaderController;
import logic.support.other.MailBox;
import logic.support.other.PaneManager;
import logic.support.other.SceneManageable;

public class HeaderBar extends HeaderController implements Observer{
		
	private MailBox mailbox;
	private ChatController chatNotif = new ChatController();
	
	
	@FXML
	private Button homeButton;
	
	@FXML
	private Button communityButton;
	
	@FXML
	private Button catalogueButton;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Button logoutButton;
	
	@FXML
	public void goToHome() {
		goToScene("home");
	}
	
	@FXML
	public void goToCatalogue() {
		goToScene("catalogue");
	}
	
	@FXML
	public void goToCommunity() {
		goToScene("community");
	}
	
	@FXML
	public void goToLogin() {
		goToScene("login");
	}
	
	@FXML
	public void logout() {
		
	}

	@Override
	public void updateHeader() {
		
		LoginBean loggedUser = (LoginBean) getBodyManager().getCurrentSceneController().getBundle().getBean("loggedUser");
		MailBox box = (MailBox) getBodyManager().getCurrentSceneController().getBundle().getObject("mailbox");
		
		if(loggedUser != null){
			loginButton.setText(loggedUser.getUserID());
		}
		else {
			loginButton.setText("Login");
		}
		
		if(box != null) {
			box.register(this);
			this.mailbox = box;
		}
		
		
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		
		if(chatNotif.getChatNotifications(mailbox)) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					loginButton.setText("notification");
				}	
			});
		}
		
		
	
	}

	


	
	
	
	
}
