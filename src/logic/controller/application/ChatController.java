package logic.controller.application;

import java.util.ArrayList;
import java.util.Date;

import logic.bean.ChatBean;
import logic.entity.ChatMessage;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.MailBox;

public class ChatController{
	
	public void sendMessage(ChatBean messageData){
		
		String senderID = messageData.getSender();
		String text = messageData.getMessageText();
		Date messageDate = new Date();
		String receiverID = messageData.getReceiver();
	
		ChatMessage message = new ChatMessage(senderID, messageDate, text);
		
		MessageSender sender = new MessageSender();
		sender.sendChatMessage(receiverID, message);
		
    }
	
	public String getLastMessageSent(MailBox box) {
		ArrayList<ChatMessage> messages = (ArrayList<ChatMessage>) box.getMessages();
		ChatMessage message = messages.get(messages.size()-1);
		return message.getText();
	}
	
	public Boolean getChatNotifications(MailBox box) {
		return !box.getNotifications(NotificationType.CHAT).isEmpty();
	}
	
}