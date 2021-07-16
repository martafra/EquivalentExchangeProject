package logic.controller.graphic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.bean.ChatBean;
import logic.bean.OrderBean;
import logic.bean.UserBean;
import logic.controller.application.BuyController;
import logic.controller.application.ChatController;
import logic.controller.application.SellController;
import logic.support.interfaces.Observer;
import logic.support.interfaces.SaleController;
import logic.support.other.Bundle;
import logic.support.other.MailBox;
import logic.support.other.SceneManageable;

public class ChatGraphicController extends SceneManageable implements Observer{

	private UserBean currentChatUser = null;
	private UserBean loggedUser;
	private OrderBean currentActiveOrder;
	private ChatController controller = new ChatController();
	private HashMap<String, ChatBox> chatBoxes = new HashMap<>();
	private SaleController saleController;
	private BuyController bController = new BuyController();
	private MailBox mailbox;
	private ChatBean lastMessageSent;
	
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
	private HBox orderBox;
	@FXML
	private Label itemOrderLabel;
	@FXML
	private Button acceptOrderButton;
	@FXML
	private Button rejectOrderButton;
 	
	
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
			chatBox.getValue().setVisible(userID.contains(filterText));
		}
	}
	
	@FXML
	public void opacifyOrderBox(){
		orderBox.setOpacity(1);
	}
	@FXML
	public void deopacifyOrderBox() {
		orderBox.setOpacity(0.6);
	}
	@FXML
	public void acceptOrder() {
		saleController.acceptOrder(currentActiveOrder);
		orderBox.setVisible(false);
	}
	@FXML
	public void rejectOrder() {
		saleController.rejectOrder(currentActiveOrder);
		orderBox.setVisible(false);
	}
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		loggedUser = (UserBean) bundle.getBean("loggedUser");
		mailbox = (MailBox) bundle.getObject("mailbox");
		mailbox.register(this);
		loadUsers();
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> filterUsers());
	}

	@Override
	public void update() {
		ChatBean chatBean = controller.getLastMessageSent(mailbox);
		
		
		if(lastMessageSent != null && lastMessageSent == chatBean)
			return;
		
		lastMessageSent = chatBean;		
		
		if(chatBean == null)
			return;
			
		HBox messageRow = null;
		String userBoxID = null;
		if(chatBean.getSender().equals(loggedUser.getUserID())){
			messageRow = (HBox) new MessageCase(chatBean).getBody();
			messageRow.setAlignment(Pos.CENTER_RIGHT);
			userBoxID = currentChatUser.getUserID();
		}
		else if(chatBean.getSender().equals(currentChatUser.getUserID())){
			messageRow = (HBox) new MessageCase(chatBean).getBody();
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
			
			chatBox.getPane().setOnMouseClicked((MouseEvent e) -> setActiveChat(userData));
			
			chatBoxes.put(userData.getUserID(), chatBox);
			chatList.getChildren().add(chatBox.getPane());
		}
		
		Map<String, ChatBean> lastMessages = controller.getLastMessagesSaved(loggedUser);
		for(Entry<String, ChatBean> lastMessage : lastMessages.entrySet()) {
			ChatBox chatBox = chatBoxes.get(lastMessage.getKey());
			chatBox.setMessage(lastMessage.getValue().getMessageText());
		}
		
		if(!activeChats.isEmpty()) {
			setActiveChat(activeChats.get(0));
		}
	}
	
	
	
	private void setActiveChat(UserBean userData) {
	
		if(currentChatUser != null) {
			ChatBox previousChatBox = chatBoxes.get(currentChatUser.getUserID());
			previousChatBox.deselect();
		}
		currentChatUser = userData;
		
		ChatBox currentChatBox = chatBoxes.get(currentChatUser.getUserID());
		currentChatBox.select();
		
		currentActiveOrder = controller.getActiveOrderByUsers(loggedUser, userData, false);
		
		if(currentActiveOrder != null) {
			bController.checkRemainingTime(currentActiveOrder);
		}
		currentActiveOrder = controller.getActiveOrderByUsers(loggedUser, userData, false);
		
		if(currentActiveOrder != null) {
			orderBox.setVisible(true);
			itemOrderLabel.setText("Deal started for Item: " + currentActiveOrder.getInvolvedItem().getItemName());
			
			if(currentActiveOrder.getBuyer().getUserID().equals(loggedUser.getUserID())) {
				saleController = new BuyController();
			}else {
				saleController = new SellController();
			}
			
		}else {
			orderBox.setVisible(false);
		}
		
		currentUserUsername.setText(currentChatUser.getUserID());
		currentUserImage.setImage(new Image(currentChatUser.getProfilePicPath()));
		updateMessageBox();
	}
	
	private void updateMessageBox() {
		if(currentChatUser == null)
			return;
		
		messageBox.getChildren().clear();
		ArrayList<ChatBean> messages = (ArrayList<ChatBean>) controller.getMessagesByUser(loggedUser, currentChatUser);
		for(ChatBean message : messages) {
			
			MessageCase messageRow = new MessageCase(message);
			HBox body = (HBox) messageRow.getBody();
			
			if(message.getSender().equals(loggedUser.getUserID())){
				body.setAlignment(Pos.CENTER_RIGHT);
			}
			else {
				body.setAlignment(Pos.CENTER_LEFT);
			}
			messageBox.getChildren().add(body);
		}
		goToBottom();
	}
	
	private void goToBottom() {
		chatScrollPane.vvalueProperty().bind(messageBox.heightProperty());
	}
}
