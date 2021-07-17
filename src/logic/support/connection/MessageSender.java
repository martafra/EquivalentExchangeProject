package logic.support.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import logic.DAO.NotificationDAO;
import logic.entity.ChatMessage;
import logic.support.other.Notification;

public class MessageSender {
	
	private List<ConnectionData> getConnections(String userID){
		SessionHandler session = new SessionHandler();
		return session.getConnectionData(userID);
	}
	
	public void sendChatMessage(String receiverID, ChatMessage message) {
		
		for(var connection : getConnections(receiverID)) {
		
			new Thread() {
				
				@Override
				public void run(){
					Socket receiver = null;
					
					try {
						receiver = new Socket(connection.getIP(), connection.getPort());
						PrintWriter writer = new PrintWriter(receiver.getOutputStream(), true);
						writer.println(MessageParser.encodeMessage(message));
						
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
			
		}
	}

	public void sendNotification(String userID, Notification notification){
		
		NotificationDAO notifDAO = new NotificationDAO();
		
		List<ConnectionData> connections = getConnections(userID);
		
		if(connections.isEmpty())
		{
			notifDAO.insertNotification(userID, notification);
			return;
		}
		
		for(var connection : connections) {
		
			new Thread() {
				
				@Override
				public void run(){
					Socket receiver = null;
					
					try {
						receiver = new Socket(connection.getIP(), connection.getPort());
						PrintWriter writer = new PrintWriter(receiver.getOutputStream(), true);
						writer.println(MessageParser.encodeNotification(notification));
						
					} catch (UnknownHostException e) {
						
						
						
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
