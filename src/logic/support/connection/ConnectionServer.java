package logic.support.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import logic.support.other.MailBox;

public class ConnectionServer implements Runnable{

	private ServerSocket handler = null; 
	private MailBox mailbox;
	private final int minPort = 4096;
	private final int maxPort = 65000;
	private Thread threadReference;

	public ConnectionServer(MailBox mailbox){
		// TODO valutare eventuale porta vuota
		try {
			
			int port;
			do {
				port = new Random().nextInt(maxPort - minPort) + minPort;
			}while(!available(port));
			
			
			handler = new ServerSocket(port);
			this.mailbox = mailbox;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		threadReference = new Thread(this);
		threadReference.start();
	
	}

	@Override
	public void run() {
		
		// TODO valutare eventuale condizione di uscita
		while(true) {
			try {
				Socket connectedUser = handler.accept();
				// TODO verbose
				System.out.println("Connessione con utente eseguita");
				var userHandler = new Thread(new ConnectionHandler(connectedUser, mailbox));
				userHandler.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Thread getThread() {
		return threadReference;
	}
	
	public ConnectionData getConnectionData() {
		
		String ip = "127.0.0.1";
		try {
			ip = InetAddress.getLocalHost().toString();
			ip = ip.substring(ip.indexOf("/")+1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return new ConnectionData(ip, handler.getLocalPort());
	}
	
	private boolean available(int port) {
	    try (Socket ignored = new Socket("localhost", port)) {
	        return false;
	    } catch (IOException ignored) {
	        return true;
	    }
	}

}
