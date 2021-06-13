package logic.controller.graphic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	private HashMap<String, ChatBox> chatBoxes = new HashMap<>();
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
		
		for(Entry<String, ChatBox> chatBox : chatBoxes.entrySet()) 
		{
			String userID = chatBox.getKey();
			HBox box = chatBox.getValue().getPane();
			box.setVisible(filterText.equals("") || userID.contains(filterText));
		}
	}
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		loggedUser = (UserBean) bundle.getBean("loggedUser");
		mailbox = (MailBox) bundle.getObject("mailbox");
		mailbox.register(this);
		loadUsers();
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filterUsers();
		});
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		ChatBean chatBean = controller.getLastMessageSent(mailbox);
		HBox messageRow = null;
		String userBoxID = null;
		if(chatBean.getSender().equals(loggedUser.getUserID())){
			messageRow = generateMessageView(chatBean);
			messageRow.setAlignment(Pos.CENTER_RIGHT);
			userBoxID = currentChatUser.getUserID();
		}
		else if(chatBean.getSender().equals(currentChatUser.getUserID())){
			messageRow = generateMessageView(chatBean);
			messageRow.setAlignment(Pos.CENTER_LEFT);
			userBoxID = currentChatUser.getUserID();
		}
		else {
			userBoxID = chatBean.getSender();
		}
		
		messageBox.getChildren().add(messageRow);
		
		goToBottom();
		chatBoxes.get(userBoxID).setMessage(chatBean.getMessageText());	
	}
	
	private void loadUsers() {
		
		ArrayList<UserBean> activeChats = (ArrayList<UserBean>) controller.getActiveChats(loggedUser);
		chatList.getChildren().clear();
		for(UserBean userData : activeChats) {
			ChatBox chatBox = new ChatBox(userData);
			
			chatBox.getPane().setOnMouseClicked(new EventHandler<>() {
				@Override
				public void handle(MouseEvent arg0) {
					setActiveChat(userData);
				}
			});
			
			chatBoxes.put(userData.getUserID(), chatBox);
			chatList.getChildren().add(chatBox.getPane());
		}
		
		if(!activeChats.isEmpty()) {
			setActiveChat(activeChats.get(0));
		}
	}
	
	
	
	private void setActiveChat(UserBean userData) {
		
		if(currentChatUser != null) {
			ChatBox previousChatBox = chatBoxes.get(userData.getUserID());
			previousChatBox.deselect();
		}
		currentChatUser = userData;
		
		ChatBox currentChatBox = chatBoxes.get(userData.getUserID());
		currentChatBox.select();
		
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
