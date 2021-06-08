package logic.controller.graphic;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import logic.support.other.MenuController;

public class ListMenuView extends MenuController implements Initializable{

	private HashMap<String, Circle> voiceNotifications = new HashMap<>();
	
	@FXML
	private VBox menuList;
	@FXML
	private HBox profileVoice;
	@FXML
	private HBox walletVoice;
	@FXML
	private HBox chatVoice;
	@FXML
	private HBox wishlistVoice;
	@FXML
	private HBox logoutVoice;
	
	@FXML
	public void goToProfile() {
		goToScene("home");
	}
	@FXML
	public void goToWallet() {
		goToScene("home");
	}
	@FXML
	public void goToChat() {
		goToScene("home");
	}
	@FXML
	public void goToWishlist() {
		goToScene("home");
	}
	@FXML
	public void logout() {
		goToScene("home");
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Circle circle = (Circle) profileVoice.getChildren().get(0);
		voiceNotifications.put("profile", circle);
		circle = (Circle) walletVoice.getChildren().get(0);
		voiceNotifications.put("wallet", circle);
		circle = (Circle) chatVoice.getChildren().get(0);
		voiceNotifications.put("chat", circle);
		circle = (Circle) wishlistVoice.getChildren().get(0);
		voiceNotifications.put("wishlist", circle);
		circle = (Circle) logoutVoice.getChildren().get(0);
		voiceNotifications.put("logout", circle);
		
		for(String key : voiceNotifications.keySet()) {
			voiceNotifications.get(key).setVisible(false);
		}
	
	}
	
	public void notifyVoice(String key) {
		voiceNotifications.get(key).setVisible(true);
	}
	
	public void denotifyVoice(String key) {
		voiceNotifications.get(key).setVisible(false);
	}
	
}
