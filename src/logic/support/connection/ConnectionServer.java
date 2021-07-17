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
	private static final int MIN_PORT = 4096;
	private static final int MAX_PORT = 65000;
	private Thread threadReference;
	private static ConnectionServer instance = null;
	
	private ConnectionServer(){
		try {
			
			int port;
			do {
				port = new Random().nextInt(MAX_PORT - MIN_PORT) + MIN_PORT;
			}while(!available(port));
			
			
			handler = new ServerSocket(port);
			
		} catch (IOException e) {
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
			
			//Do nothing
			
		}
	}
	
	public void setMailBox(MailBox mailbox) {
		this.mailbox = mailbox;
	}
	
	@Override
	public void run() {
		
		while(true) {
			try {
				if(!running.get())
					return;
				Socket connectedUser = handler.accept();
				var userHandler = new Thread(new ConnectionHandler(connectedUser, mailbox));
				userHandler.start();
			} catch(SocketException e) {
				if(!running.get())
					return;
			}catch (IOException e) {
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
