package logic.support.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import logic.support.other.MailBox;

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
			while(true) {
				String msg = reader.readLine();
				if(msg == null)
					break;
				
				System.out.println("message->" + msg);
				mailbox.addMessage(msg);
				
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
