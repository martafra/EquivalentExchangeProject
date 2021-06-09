package logic.controller.graphic;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
	private Boolean changeState = false;
	private Boolean logged = false;
	private ChatController chatNotif = new ChatController();
	private ProfileBox profileBox = new ProfileBox();
	
	
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
	private HBox headerBox;
	
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

	@Override
	public void updateHeader() {
		
		UserBean loggedUser = (UserBean) getBodyManager().getCurrentSceneController().getBundle().getBean("loggedUser");
		MailBox box = (MailBox) getBodyManager().getCurrentSceneController().getBundle().getObject("mailbox");
		
		if(loggedUser != null && !logged){
			changeState = true;
			logged = true;
		}
		if(loggedUser == null && logged) {
			changeState = true;
			logged = false;
		}
		
		switchProfileView(loggedUser);
		
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
		
		ListMenuView menu = (ListMenuView) this.getMenuManager();
			
		if(chatNotif.getChatNotifications(mailbox)) {
			menu.notifyVoice("chat");
		}
		
		
	
	}

	

	private void switchProfileView(UserBean loggedUser) {
		
		if(Boolean.TRUE.equals(changeState)) {
			changeState = false;
			headerBox.getChildren().remove(loginBox);
			headerBox.getChildren().add(profileBox);
			profileBox.setProfileName(loggedUser.getUserID());
			profileBox.setProfilePic(loggedUser.getProfilePicPath());
			profileBox.setOnMouseClicked(new EventHandler<>() {

				@Override
				public void handle(MouseEvent arg0) {
					getBodyManager().switchMenu();
				}
				
			});
		}
	}
	
	
	
	
}
