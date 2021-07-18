package logic.controller.graphic;

import logic.bean.LoginBean;
import logic.bean.UserBean;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.controller.application.LoginController;
import logic.support.exception.WrongLoginCredentialsException;
import logic.support.other.SceneManageable;

public class LoginView extends SceneManageable{

    private LoginBean bean = new LoginBean();
    private LoginController log = new LoginController();
    
    @FXML
    private TextField userText;
    
    @FXML
    private PasswordField passText;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label errorLabel;
    
    
    @FXML 
    public void login(Event e){
    	bean.setUserID(userText.getText());
    	bean.setPassword(passText.getText());
    	try {
			log.login(bean);
			UserBean me = log.getUserByLoginData(bean);
	    	bundle.addBean("loggedUser", me);
	    	bundle.addObject("mailbox", log.connect(me));
	    	goToScene("home");
		} catch (WrongLoginCredentialsException e1) {
			errorLabel.setText("Wrong credentials supplied, check your user ID and password!");
		}

    }
    
    @FXML
    public void goToRegistration() {
    	goToScene("register");
    }
	
	@Override
	public void onExit() {
		userText.setText("");
		passText.setText("");
		errorLabel.setText("");
	}

}