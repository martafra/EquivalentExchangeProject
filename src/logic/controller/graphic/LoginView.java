package logic.controller.graphic;

import logic.bean.LoginBean;
import logic.bean.UserBean;
import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import logic.controller.application.LoginController;
import logic.support.other.SceneManageable;

public class LoginView extends SceneManageable{

    private LoginBean bean = new LoginBean();
    private LoginController log = new LoginController();
    
    @FXML
    private VBox vbox;
    
    @FXML
    private TextField userText;
    
    @FXML
    private TextField passText;
    
    @FXML
    private TextField otherUserID;
    
    @FXML 
    public void login(Event e) throws IOException {
    	bean.setUserID(userText.getText());
    	bean.setPassword(passText.getText());
    	Boolean result = log.login(bean);

    	if(result) {
    		bundle.addBean("loggedUser", bean);
    		
    		
    		UserBean chatUser = new UserBean();
    		chatUser.setUserID(otherUserID.getText());
    		bundle.addBean("chatUser", chatUser);
    		bundle.addObject("mailbox", log.connect(bean));
    		
    		goToScene("chat");
    	}
    	
    	
    }
    
    @FXML
    public void goToRegistration() {
    	goToScene("register");
    }
	
	


  

}