package logic.support.connection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.entity.ChatMessage;

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
	
}
