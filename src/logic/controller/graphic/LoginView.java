package logic.controller.graphic;

import logic.bean.LoginBean;

import java.io.FileInputStream;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.controller.application.LoginController;

public class LoginView extends Application{

    private LoginBean bean = new LoginBean();
    private LoginController log = new LoginController();
    
    @FXML
    private TextField userText;
    
    @FXML
    private TextField passText;
    
    @FXML 
    public void login(Event e) {
    	
    	bean.setUserID(userText.getText());
    	bean.setPassword(passText.getText());
    	
    	Boolean result = log.login(bean);
    	
    	System.out.println(result);
    	
    	
    }
    
	
    public static void main(String[] args) 
    {
        Application.launch(args);
    }
    

    
    
    @Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
        // Path to the FXML File
        String fxmlDocPath = "src/logic/view/LoginView.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
 
        // Create the Pane and all Details
        VBox root = (VBox) loader.load(fxmlStream);
 
        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("A simple FXML Example");
        // Display the Stage
        stage.show();
		
	}


  

}