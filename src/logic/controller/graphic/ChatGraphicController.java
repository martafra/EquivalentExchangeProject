package logic.controller.graphic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
	private HashMap<String, HBox> chatBoxes = new HashMap<>();
	private MailBox mailbox;
	
	@FXML
	private TextField searchField;
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
	private ScrollPane chatScrollPane;
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
	
	@FXML
	public void filterUsers(){
		String filterText = searchField.getText();
		
		System.out.println("Hello" + filterText);
		
		for(String userID : chatBoxes.keySet()) 
			chatBoxes.get(filterText).setVisible(userID.contains(filterText) || filterText.equals(""));
		
		
	}
	
	@Override
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
		HBox messageRow = null;
		if(chatBean.getSender().equals(loggedUser.getUserID())){
			messageRow = generateMessageView(chatBean);
			messageRow.setAlignment(Pos.CENTER_RIGHT);
		}
		if(chatBean.getSender().equals(currentChatUser.getUserID())){
			messageRow = generateMessageView(chatBean);
			messageRow.setAlignment(Pos.CENTER_LEFT);
		}
		messageBox.getChildren().add(messageRow);
		
		goToBottom();
		//TODO gestire anteprime messaggi chat al lato
		
		
		
		
	}
	
	private void loadUsers() {
		
		ArrayList<UserBean> activeChats = (ArrayList<UserBean>) controller.getActiveChats(loggedUser);
		chatList.getChildren().clear();
		for(UserBean userData : activeChats) {
			HBox chatBox = generateChatBox(userData);
			chatBoxes.put(userData.getUserID(), chatBox);
			chatList.getChildren().add(chatBox);
		}
	}
	
	
	
	private void setActiveChat(UserBean userData) {
		
		if(currentChatUser != null) {
			HBox previousChatBox = chatBoxes.get(userData.getUserID());
			previousChatBox.setStyle(null);
		}
		currentChatUser = userData;
		
		HBox currentChatBox = chatBoxes.get(userData.getUserID());
		currentChatBox.setStyle("-fx-background-color: #77777733");
		
		currentUserUsername.setText(userData.getUserID());
		currentUserImage.setImage(new Image(userData.getProfilePicPath()));
		updateMessageBox();
	}
	
	private void updateMessageBox() {
		if(currentChatUser == null)
			return;
		
		messageBox.getChildren().clear();
		ArrayList<ChatBean> messages = (ArrayList<ChatBean>) controller.getMessagesByUser(loggedUser, currentChatUser);
		for(ChatBean message : messages) {
			
			HBox messageRow = generateMessageView(message);
			
			if(message.getSender().equals(loggedUser.getUserID())){
				messageRow.setAlignment(Pos.CENTER_RIGHT);
			}
			else {
				messageRow.setAlignment(Pos.CENTER_LEFT);
			}
			messageBox.getChildren().add(messageRow);
		}
		goToBottom();
	}
	
	
	private HBox generateChatBox(UserBean userData) {
		
		HBox chatBox = null;
		try {
			chatBox = new FXMLLoader(getClass().getResource("/logic/view/ChatBox.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(chatBox == null)
			return new HBox();
		
		ImageView profileImage = (ImageView) chatBox.getChildren().get(0);
		profileImage.setImage(new Image(userData.getProfilePicPath()));
		
		VBox infoBox = (VBox) chatBox.getChildren().get(1);
		
		for(Node node : infoBox.getChildren()) {

			if(node.getId() == null) {
				continue;
			}
			
			switch(node.getId()) {
				case "usernameLabel":
					((Label) node).setText(userData.getUserID());
					
					break;
				case "messageLabel":
					((Label) node).setText("message");
					
					break;
				default:
					break;
			}
		}
		
		chatBox.setOnMouseClicked(new EventHandler<>() {
			@Override
			public void handle(MouseEvent arg0) {
				setActiveChat(userData);
			}
		});
		
		return chatBox;
	}
	
	private HBox generateMessageView(ChatBean message) {
		DateFormat format = new SimpleDateFormat("HH:mm");
		HBox messageRow = null;
		try {
			messageRow = new FXMLLoader(getClass().getResource("/logic/view/MessageBox.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(messageRow == null)
			return new HBox();
		
		VBox messageContainer = (VBox) messageRow.getChildren().get(0);
		
		for(Node node : messageContainer.getChildren()) {

			if(node.getId() == null) {
				continue;
			}
			
			switch(node.getId()) {
				case "textMessage":
					((Text) node).setText(message.getMessageText());
					
					break;
				case "date":
					((Label) node).setText(format.format(message.getDate()));
					
					break;
				default:
					break;
			}
		}
		
		return messageRow;
	}
	
	private void goToBottom() {
		chatScrollPane.vvalueProperty().bind(messageBox.heightProperty());
	}
}
