package logic.support.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import logic.dao.NotificationDAO;
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
			
					try (Socket receiver = new Socket(connection.getIP(), connection.getPort())){
						
						PrintWriter writer = new PrintWriter(receiver.getOutputStream(), true);
						writer.println(MessageParser.encodeMessage(message));
						
					} catch (IOException e) {
						//Do nothing
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
					
					
					try(Socket receiver = new Socket(connection.getIP(), connection.getPort())){
						
						PrintWriter writer = new PrintWriter(receiver.getOutputStream(), true);
						writer.println(MessageParser.encodeNotification(notification));
						
					} catch (IOException e) {
						//Do nothing
					}
				}
			}.start();
		}
	}
}
