package logic.entrypoint;

import java.io.*;
import java.net.*;
import logic.support.connection.eeserver.ClientHandler;
import logic.support.connection.eeserver.ConnectionHandler;

public class MainServer{
	
	private static final Integer PORT = 4096;
	
	public static void main(String[] args) {
	
		ConnectionHandler session = new ConnectionHandler();
		try (ServerSocket server = new ServerSocket(PORT);){
			
			while(true) {
				Socket client = server.accept();
				
				if(client == null)
					break;
				
				new Thread(new ClientHandler(session, client)).start();
				
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		
		
	}
}

