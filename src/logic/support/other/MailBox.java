package logic.support.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logic.entity.Message;

public class MailBox extends Subject{

	ArrayList<Message> messages = new ArrayList<>();
	
	public List<Message> getState() {
		return messages;
	}
	
	public void addMessage(String message) {
		
		messages.add(parseMessage(message));
		notifyObservers();
	}
	
	private Message parseMessage(String messageString) {
		
		if(messageString == ""){
			return null;
		}
		String[] params = messageString.split(";");
		HashMap<String, String> messageFields = new HashMap<>();

		for(String param : params){
			System.out.println(param);
			String[] keyValue = param.split(":");
			messageFields.put(keyValue[0], keyValue[1]);
		}
		return new Message(messageFields.get("username"), messageFields.get("date"), messageFields.get("text"));
		
	}
	
	
}
