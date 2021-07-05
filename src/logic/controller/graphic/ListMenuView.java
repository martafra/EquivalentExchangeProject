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
	private final String chatName = "chat";
	private final String profileName = "profile";
	private final String walletName = "wallet";
	private final String wishlistName = "wishlist";
	private final String logoutName = "logout";
	
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
		denotifyVoice(profileName);
		goToScene("profile");
	}
	@FXML
	public void goToWallet() {
		denotifyVoice(walletName);
		goToScene("wallet");
	}
	@FXML
	public void goToChat() {
		denotifyVoice(chatName);
		goToScene("chat");
	}
	@FXML
	public void goToWishlist() {
		denotifyVoice(wishlistName);
		goToScene("wishlist");
	}
	@FXML
	public void logout() {
		HeaderBar hb = (HeaderBar) this.getAttachedHeader();
		hb.logout();
		goToScene("login");
		switchMenu();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Circle circle = (Circle) profileVoice.getChildren().get(0);
		voiceNotifications.put(profileName, circle);
		circle = (Circle) walletVoice.getChildren().get(0);
		voiceNotifications.put(walletName, circle);
		circle = (Circle) chatVoice.getChildren().get(0);
		voiceNotifications.put(chatName, circle);
		circle = (Circle) wishlistVoice.getChildren().get(0);
		voiceNotifications.put(wishlistName, circle);
		circle = (Circle) logoutVoice.getChildren().get(0);
		voiceNotifications.put(logoutName, circle);
		
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
