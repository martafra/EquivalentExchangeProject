package logic.controller.graphic;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import logic.bean.LoginBean;
import logic.support.other.HeaderController;
import logic.support.other.PaneManager;
import logic.support.other.SceneManageable;

public class HeaderBar extends HeaderController{
		
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
	public void update() {
		
		LoginBean loggedUser = (LoginBean) getBodyManager().getCurrentSceneController().getBundle().get("loggedUser");
		if(loggedUser != null){
			loginButton.setText(loggedUser.getUserID());
		}
		else {
			loginButton.setText("Login");
		}
		
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	


	
	
	
	
}
