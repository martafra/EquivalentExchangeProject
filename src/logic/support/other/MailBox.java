package logic.support.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logic.entity.ChatMessage;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageParser;

public class MailBox extends Subject{

	ArrayList<ChatMessage> messages = new ArrayList<>();
	ArrayList<Notification> notifications = new ArrayList<>();
	
	public List<ChatMessage> getMessages() {
		return messages;
	}
	public List<Notification> getNotifications(){
		return notifications;
	}

	public List<Notification> getNotifications(NotificationType type){
		ArrayList<Notification> filteredNotifications = new ArrayList<>();
		for(Notification n : notifications) {
			if(n.getType().equals(type))
				filteredNotifications.add(n);
		}
		return filteredNotifications;
	}
	
	public void addMessage(String message) {
		messages.add(MessageParser.parseChatMessage(message));
		notifyObservers();
	}
	
	public void addNotification(String notification) {
		notifications.add(MessageParser.parseNotification(notification));
		notifyObservers();
	}
	
	
	
}
