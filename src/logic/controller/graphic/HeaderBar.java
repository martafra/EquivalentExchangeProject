package logic.controller.graphic;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.ChatController;
import logic.support.interfaces.Observer;
import logic.support.other.HeaderController;
import logic.support.other.MailBox;


public class HeaderBar extends HeaderController implements Observer{
		
	private MailBox mailbox;
	private Boolean logged;
	private ChatController chatNotif = new ChatController();
	ImageView profilePic = new ImageView();
	
	
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
	private Button postAnAdButton;
	
	@FXML
	private HBox loginBox;
	
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
	public void goToPostAnAd() {
		goToScene("postad");
	}
	
	@FXML
	public void logout() {
		
	}
	

	@Override
	public void updateHeader() {
		
		UserBean loggedUser = (UserBean) getBodyManager().getCurrentSceneController().getBundle().getBean("loggedUser");
		MailBox box = (MailBox) getBodyManager().getCurrentSceneController().getBundle().getObject("mailbox");
		
		if(loggedUser != null){
			logged = true;
		}
		else {
			logged = false;
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
			
		}
		
		
	
	}

	


	
	
	
	
}
