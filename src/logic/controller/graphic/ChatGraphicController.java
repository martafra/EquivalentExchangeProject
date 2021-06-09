package logic.controller.graphic;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.bean.ChatBean;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.ChatController;
import logic.support.connection.MessageSender;
import logic.support.interfaces.Observer;
import logic.support.other.Bundle;
import logic.support.other.MailBox;
import logic.support.other.SceneManageable;

public class ChatGraphicController extends SceneManageable implements Observer{

	private UserBean currentChatUser = null;
	private UserBean loggedUser;
	private ChatController controller = new ChatController();
	private HashMap<String, ArrayList<ChatBean>> sortedMessages = new HashMap<>();
	private MailBox mailbox;
	
	@FXML
	private TextArea textMessage;
	@FXML
	private Button sendButton;
	@FXML
	private VBox chatList;
	@FXML
	private VBox messageBox;
	@FXML
	private Label currentUserUsername;
	@FXML
	private ImageView currentUserImage;
	
	@FXML
	public void sendMessage() {
		
		if(textMessage.getText().equals(""))
			return;
		
		ChatBean message = new ChatBean();
		message.setSender(loggedUser.getUserID());
		message.setReceiver(currentChatUser.getUserID());
		message.setMessageText(textMessage.getText());
		
		controller.sendMessage(message);
		
	}
	
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		loggedUser = (UserBean) bundle.getBean("loggedUser");
		mailbox = (MailBox) bundle.getObject("mailbox");
		mailbox.register(this);
		
		loadUsers();
		
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		ChatBean chatBean = controller.getLastMessageSent(mailbox);
		sortedMessages.get(chatBean.getSender()).add(chatBean);
		
		updateMessageBox();
		
		
	}
	
	private void loadUsers() {
		ArrayList<UserBean> activeChats = (ArrayList<UserBean>) controller.getActiveChats(loggedUser);
		for(UserBean userData : activeChats) {
			
			sortedMessages.put(userData.getUserID(), new ArrayList<>());
			
			HBox userChatBox = new HBox();
			userChatBox.setAlignment(Pos.CENTER_LEFT);
			ImageView userImage = new ImageView(new Image(userData.getProfilePicPath()));
			userImage.setFitWidth(50);
			userImage.setFitHeight(50);
			Label userLabel = new Label(userData.getUserID());
			userChatBox.getChildren().add(userImage);
			userChatBox.getChildren().add(userLabel);
			
			chatList.getChildren().add(userChatBox);
			userChatBox.setOnMouseClicked(new EventHandler<>() {
				@Override
				public void handle(MouseEvent arg0) {
					setActiveChat(userData);
				}
			});
		}
	}
	
	private void setActiveChat(UserBean userData) {
		currentChatUser = userData;
		currentUserUsername.setText(userData.getUserID());
		currentUserImage.setImage(new Image(userData.getProfilePicPath()));
	}
	
	private void updateMessageBox() {
		if(currentChatUser == null)
			return;
		
		messageBox.getChildren().removeAll();
		
		for(ChatBean message : sortedMessages.get(currentChatUser.getUserID())) {
			messageBox.getChildren().add(new Label(message.getSender() + " > " + message.getMessageText()));
		}
		
	}
	
}
