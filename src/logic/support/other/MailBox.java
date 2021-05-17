package logic.support.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logic.entity.ChatMessage;
import logic.support.connection.MessageParser;

public class MailBox extends Subject{

	ArrayList<ChatMessage> messages = new ArrayList<>();
	
	public List<ChatMessage> getState() {
		return messages;
	}
	
	public void addMessage(String message) {
		
		messages.add(MessageParser.parseChatMessage(message));
		notifyObservers();
	}
	
	
	
}
