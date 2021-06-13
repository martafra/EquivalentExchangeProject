package logic.controller.graphic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import logic.bean.UserBean;

public class ChatBox extends GraphicWidget{

	private HBox boxBody;
	
	public ChatBox(UserBean userData) {
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/ChatBox.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		ImageView profileImage = (ImageView) getComponent("profileImage");
		profileImage.setImage(new Image(userData.getProfilePicPath()));
		
		Label username = (Label) getComponent("usernameLabel");
		username.setText(userData.getUserID());
		
	}
	
	public void setMessage(String message) {
		Label messageLabel = (Label) getComponent("messageLabel");
		messageLabel.setText(message);
	}
	
	public HBox getPane() {
		return this.boxBody;
	}
	
	public void select() {
		getPane().setStyle("-fx-background-color: #77777733");
	}

	public void deselect() {
		getPane().setStyle(null);
	}
	
}
