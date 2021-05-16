package logic.support.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import logic.support.other.MailBox;

public class ConnectionServer implements Runnable{

	private ServerSocket handler = null; 
	private MailBox mailbox;

	public ConnectionServer(MailBox mailbox){
		// TODO valutare eventuale porta vuota
		try {
			handler = new ServerSocket(4999);
			this.mailbox = mailbox;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		new Thread(this).start();
	}

	@Override
	public void run() {
		
		// TODO valutare eventuale condizione di uscita
		while(true) {
			try {
				Socket connectedUser = handler.accept();
				// TODO verbose
				System.out.println("Connessione con utente eseguita");
				new Thread(new ConnectionHandler(connectedUser, mailbox)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		
	    
		
	}

}
