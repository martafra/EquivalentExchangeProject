package logic.support.connection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.entity.ChatMessage;
import logic.support.other.Notification;

public class MessageParser {

	public static Map<String, String> parseMessage(String message){
		if(message == "" || message == null)
			return null;
		
		String[] params = message.split(";");
		HashMap<String, String> messageFields = new HashMap<>();

		for(String param : params){
			String[] keyValue = param.split(":");
			messageFields.put(keyValue[0], keyValue[1]);
		}
		return messageFields;
	}
	
	public static List<ConnectionData> parseIPList(String ipList){
		
		if(ipList.equals("") || ipList.equals("{null}"))
			return new ArrayList<>();
		
		ArrayList<ConnectionData> connectionsList = new ArrayList<>();
		ipList = ipList.substring(1, ipList.length()-1); //Rimuove '{' e '}' agli estremi della stringa
		String[] parsedList = ipList.split("_");
		
		for(String connection : parsedList) {
			connectionsList.add(new ConnectionData(connection.replace('-', ':')));
		}
		return connectionsList;
	}

	public static String encodeMessage(ChatMessage message) {
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");
		return "type:chat;sender:"+message.getSender()+";date:"+
				format.format(message.getDate())+";text:"+message.getText();
	}

	public static ChatMessage parseChatMessage(String message) {
		var messageFields = parseMessage(message);
		if(messageFields == null)
			return new ChatMessage("", "", "");
		return new ChatMessage(messageFields.get("sender"), messageFields.get("date"), messageFields.get("text"));
	}

	public static String encodeNotification(Notification notification) {
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");
		String result = "type:notification/" + notification.getType().toString() + ";";
		result += "sender:" + notification.getSender() + ";";
		result += "date:" + format.format(notification.getDate()) + ";";
		result += "options:{";

		HashMap<String, String> options = (HashMap<String, String>) notification.getParameters(); 

		if(options.isEmpty()){
			result += "};";
			return result;
		}

		for(String key : options.keySet()){
			result += key + "-" + options.get(key) + "_";
		}
		result = result.substring(0, result.length()-1);
		result += "}";	
		return result;
	}
	
	public static Notification parseNotification(String notificationString){
		var notificationFields = parseMessage(notificationString);
		Notification notification = new Notification();
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");

		notification.setSender(notificationFields.get("sender"));
		try {
			notification.setDate(format.parse(notificationFields.get("date")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//TODO inserire campi mancanti
		return notification;

	}

}
