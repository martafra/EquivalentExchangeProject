package logic.query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageQuery extends Query{

	public String getMessagesByUsers(String username1, String username2) {
		username1 = quote(username1);
		username2 = quote(username2);
		
		String query = "SELECT * FROM messages WHERE (senderID = %s AND receiverID = %s) "
					 + " OR (senderID = %s AND receiverID = %s) ORDER BY sDateTime ASC";
		return String.format(query, username1, username2, username2, username1);
	}
	
	public String addMessage(String senderID, String receiverID, String text, Date date) {
		
		senderID = quote(senderID);
		receiverID = quote(receiverID);
		text = quote(text);
		String dateText = "";
		if(date != null)	
			dateText = quote(new SimpleDateFormat(dateTimeFormat).format(date));
		
		String query = "INSERT INTO messages (senderID, receiverID, body, sDateTime) "
					 + "VALUES (%s, %s, %s, %s);";
		
		return String.format(query, senderID, receiverID, text, dateText);
	}
	
}
