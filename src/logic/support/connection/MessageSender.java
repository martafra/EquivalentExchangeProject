package logic.support.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import logic.entity.ChatMessage;

public class MessageSender {
	
	public void sendChatMessage(String receiverID, ChatMessage message) {
		SessionHandler session = new SessionHandler();
		ArrayList<ConnectionData> connections =  (ArrayList<ConnectionData>) session.getConnectionData(receiverID);
		
		for(var connection : connections) {
		
			new Thread() {
				
				@Override
				public void run(){
					Socket receiver = null;
					
					try {
						receiver = new Socket(connection.getIP(), connection.getPort());
						PrintWriter writer = new PrintWriter(receiver.getOutputStream(), true);
						writer.println(MessageParser.encodeMessage(message));
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
	
}
