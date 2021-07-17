package logic.controller.graphic;

import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import logic.support.other.MenuController;

public class ListMenuView extends MenuController implements Initializable{

	private HashMap<String, Circle> voiceNotifications = new HashMap<>();
	private static final String CHAT_NAME = "chat";
	private static final String PROFILE_NAME = "profile";
	private static final String WALLET_NAME = "wallet";
	private static final String WISHLIST_NAME = "wishlist";
	private static final String LOGOUT_NAME = "logout";
	
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
		denotifyVoice(PROFILE_NAME);
		goToScene(PROFILE_NAME);
	}
	@FXML
	public void goToWallet() {
		denotifyVoice(WALLET_NAME);
		goToScene(WALLET_NAME);
	}
	@FXML
	public void goToChat() {
		denotifyVoice(CHAT_NAME);
		goToScene(CHAT_NAME);
	}
	@FXML
	public void goToWishlist() {
		denotifyVoice(WISHLIST_NAME);
		goToScene(WISHLIST_NAME);
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
		voiceNotifications.put(PROFILE_NAME, circle);
		circle = (Circle) walletVoice.getChildren().get(0);
		voiceNotifications.put(WALLET_NAME, circle);
		circle = (Circle) chatVoice.getChildren().get(0);
		voiceNotifications.put(CHAT_NAME, circle);
		circle = (Circle) wishlistVoice.getChildren().get(0);
		voiceNotifications.put(WISHLIST_NAME, circle);
		circle = (Circle) logoutVoice.getChildren().get(0);
		voiceNotifications.put(LOGOUT_NAME, circle);
		
		for(Entry<String, Circle> entry : voiceNotifications.entrySet()) {
			entry.getValue().setVisible(false);
		}
	
	}
	
	public void notifyVoice(String key) {
		voiceNotifications.get(key).setVisible(true);
	}
	
	public void denotifyVoice(String key) {
		voiceNotifications.get(key).setVisible(false);
	}
	
}
