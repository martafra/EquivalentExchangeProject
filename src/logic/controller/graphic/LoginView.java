package logic.controller.graphic;

import logic.bean.LoginBean;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.controller.application.LoginController;
import logic.support.interfaces.SceneManageable;
import logic.support.other.LoginSession;
import logic.support.other.PaneManager;

public class LoginView implements SceneManageable{

    private LoginBean bean = new LoginBean();
    private LoginController log = new LoginController();
    private PaneManager myPaneManager;
    
    @FXML
    private VBox vbox;
    
    @FXML
    private TextField userText;
    
    @FXML
    private TextField passText;
    
    @FXML 
    public void login(Event e) throws IOException {
    	bean.setUserID(userText.getText());
    	bean.setPassword(passText.getText());
    	Boolean result = log.login(bean);
    	
    	
    	
    	
    	if(result) {
    		myPaneManager.setScene("loginprova");
    	}
    	
    	
    }
	
	@Override
	public void setPaneManager(PaneManager manager) {
		myPaneManager = manager;
		
	}
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}


  

}