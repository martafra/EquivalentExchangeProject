package logic.query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationQuery extends Query{
	
	public String insertNotification(String sender, String receiver, String type, String options, Date date) {
		
		String dateString = "null";
		if(date != null) {
			dateString = new SimpleDateFormat(dateTimeFormat).format(date);
			dateString = quote(dateString);
		}
		
		sender = quote(sender);
		receiver = quote(receiver);
		type = quote(type);
		options = quote(options);
		
		
		String query = "INSERT INTO Notification (sender, receiver, notificationType, options, date) "
				     + "VALUES (%s, %s, %s, %s, %s);";
					
		return String.format(query, sender, receiver, type, options, dateString);
	}
	
	public String getNotifications(String userID) {
		userID = quote(userID);
		String query = "SELECT * FROM Notification WHERE receiver = %s;";
		
		return String.format(query, userID);
	}
	
}
