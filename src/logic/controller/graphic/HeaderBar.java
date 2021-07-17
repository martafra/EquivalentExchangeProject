package logic.controller.graphic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import logic.bean.UserBean;
import logic.controller.application.ChatController;
import logic.controller.application.LoginController;
import logic.support.interfaces.Observer;
import logic.support.other.HeaderController;
import logic.support.other.MailBox;


public class HeaderBar extends HeaderController implements Observer{
		
	private MailBox mailbox;
	private Boolean changeState = false;
	private Boolean logged = false;
	private UserBean loggedUser;
	private ChatController chatNotif = new ChatController();
	private LoginController logController = new LoginController();
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
	

	@Override
	public void updateHeader() {
		
		loggedUser = (UserBean) getBodyManager().getCurrentSceneController().getBundle().getBean("loggedUser");
		MailBox box = (MailBox) getBodyManager().getCurrentSceneController().getBundle().getObject("mailbox");
		
		if(loggedUser != null && !logged){
			changeState = true;
			logged = true;
		}
		if(loggedUser == null && logged) {
			changeState = true;
			logged = false;
		}
		
		if(loggedUser != null)
			switchProfileView(loggedUser);
		
		if(box != null) {
			box.register(this);
			this.mailbox = box;
		}
		
		
	}

	@Override
	public void update() {
		
		ListMenuView menu = (ListMenuView) this.getMenuManager();
			
		if(Boolean.TRUE.equals(chatNotif.getChatNotifications(mailbox))) {
			menu.notifyVoice("chat");
		}
		
		
	
	}

	public void logout() {
		if(Boolean.TRUE.equals(logged))
		{
			logController.logout(loggedUser);
			this.getBodyManager().getCurrentSceneController().getBundle().removeBean("loggedUser");
			updateHeader();
		}
	}

	private void switchProfileView(UserBean loggedUser) {
		
		if(Boolean.TRUE.equals(changeState)) {
			changeState = false;
			if(Boolean.TRUE.equals(logged))
			{
				headerBox.getChildren().remove(loginBox);
				headerBox.getChildren().add(profileBox);
				profileBox.setProfileName(loggedUser.getUserID());
				profileBox.setProfilePic(loggedUser.getProfilePicPath());
				profileBox.setOnMouseClicked((MouseEvent e) ->  getBodyManager().switchMenu());
			}else {
				headerBox.getChildren().remove(profileBox);
				headerBox.getChildren().add(loginBox);
			}
			
		}
	}

	@Override
	public void onLoad() {
		//Do nothing on load
		
	}

	
	
	
	
	
}
