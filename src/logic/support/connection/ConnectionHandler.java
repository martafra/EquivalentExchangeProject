package logic.support.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import logic.enumeration.NotificationType;
import logic.support.other.MailBox;
import logic.support.other.Notification;

public class ConnectionHandler implements Runnable {
	
	private Socket user;
	private MailBox mailbox;
	
	public ConnectionHandler(Socket user, MailBox mailbox) {
		this.user = user;
		this.mailbox = mailbox;
	}
	@Override
	public void run() {
		try {
			var reader = new BufferedReader(new InputStreamReader(user.getInputStream()));
			String msg = reader.readLine();
			if(msg == null)
				return;
			Map<String, String> message = MessageParser.parseMessage(msg);
			String type = message.get("type");
			int index;
			if((index = type.indexOf('/')) == -1)
				index = type.length();
				
			type = type.substring(0, index);
				
			switch(type) {
				case "chat":
					mailbox.addMessage(msg);
					Notification chatNotification = new Notification();
					chatNotification.setType(NotificationType.CHAT);
					chatNotification.setSender(message.get("sender"));
					chatNotification.setDate(new Date());
					mailbox.addNotification(MessageParser.encodeNotification(chatNotification));
					break;
				case "notification":
					mailbox.addNotification(msg);
					break;
				default:
					break;
			}		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
