package logic.support.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import logic.support.other.MailBox;

public class ConnectionServer implements Runnable{

	private ServerSocket handler = null; 
	private MailBox mailbox;
	private AtomicBoolean running = new AtomicBoolean(false);
	private final int minPort = 4096;
	private final int maxPort = 65000;
	private Thread threadReference;
	private static ConnectionServer instance = null;
	
	private ConnectionServer(){
		// TODO valutare eventuale porta vuota
		try {
			
			int port;
			do {
				port = new Random().nextInt(maxPort - minPort) + minPort;
			}while(!available(port));
			
			
			handler = new ServerSocket(port);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		threadReference = new Thread(this);
	}
	
	public static ConnectionServer getInstance() {
		if(instance == null)
			instance = new ConnectionServer();
		return instance;
	}
	
	public static void resetInstance() {
		instance = null;
	}
	
	public void startServer() {
		this.running.set(true);
		threadReference.start();
	}

	public void stopServer() {
		this.running.set(false);
		try {
			this.handler.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Non sono riuscito a chiudere il server");
		}
	}
	
	public void setMailBox(MailBox mailbox) {
		this.mailbox = mailbox;
	}
	
	@Override
	public void run() {
		
		// TODO valutare eventuale condizione di uscita
		while(true) {
			try {
				if(!running.get())
					break;
				Socket connectedUser = handler.accept();
				// TODO verbose
				System.out.println("Connessione con utente eseguita");
				var userHandler = new Thread(new ConnectionHandler(connectedUser, mailbox));
				userHandler.start();
			} catch(SocketException e) {
				if(!running.get())
					break;
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
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
