package logic.controller.graphic;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	private UserBean receiver;
	private LoginBean loggedUser;
	private ChatController controller = new ChatController();
	private MailBox mailbox;
	
	@FXML
	private TextField message;
	
	@FXML
	private VBox messageArea;
	
	@FXML
	private Button sendButton;
	
	@FXML
	public void sendMessage() {
		
		if(message.getText().equals(""))
			return;
		
		ChatBean chatBean = new ChatBean();
		chatBean.setMessageText(message.getText());
		chatBean.setSender(loggedUser.getUserID());
		chatBean.setReceiver(receiver.getUserID());
		
		controller.sendMessage(chatBean);
		
		Label messageLabel = new Label(loggedUser.getUserID() + " > " + message.getText());
		messageArea.getChildren().add(messageLabel);
	}
	
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		receiver = (UserBean) bundle.getBean("chatUser");
		loggedUser = (LoginBean) bundle.getBean("loggedUser");
		mailbox = (MailBox) bundle.getObject("mailbox");
		
		mailbox.register(this);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		String textMessage = receiver.getUserID() + " > " + controller.getLastMessageSent(mailbox);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Label messageLabel = new Label(textMessage);
				messageArea.getChildren().add(messageLabel);
			}	
		});
		
		
	}
	
	
}
