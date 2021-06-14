package logic.support.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import logic.entity.ChatMessage;
import logic.support.other.Notification;

public class MessageSender {
	
	private List<ConnectionData> getConnections(String userID){
		SessionHandler session = new SessionHandler();
		ArrayList<ConnectionData> connections =  (ArrayList<ConnectionData>) session.getConnectionData(userID);
		return connections;
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
						//TODO verbose
						System.out.println(MessageParser.encodeMessage(message));
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			
		}
	}

	public void sendNotification(String userID, Notification notification){
		for(var connection : getConnections(userID)) {
		
			new Thread() {
				
				@Override
				public void run(){
					Socket receiver = null;
					
					try {
						receiver = new Socket(connection.getIP(), connection.getPort());
						PrintWriter writer = new PrintWriter(receiver.getOutputStream(), true);
						writer.println(MessageParser.encodeNotification(notification));
						//TODO verbose
						System.out.println(MessageParser.encodeNotification(notification));
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
