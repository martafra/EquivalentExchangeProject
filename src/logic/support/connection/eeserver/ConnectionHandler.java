package logic.support.connection.eeserver;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import logic.support.connection.ConnectionData;

public class ConnectionHandler {
	
	private HashMap<String, ArrayList<ConnectionData>> onlineUsers = new HashMap<>();
	private AtomicBoolean isUsing = new AtomicBoolean(false);
	
	// add an user to onlineUsers hashmap
	// if them exist, add new socket to the user socket list
	public void addUser(String username, ConnectionData client) {
		ArrayList<ConnectionData> list = onlineUsers.get(username);
		
		if (list == null) {
			ArrayList<ConnectionData> newList = new ArrayList<>();
			newList.add(client);
			onlineUsers.put(username, newList);
			return;
		}
		
		if(list.contains(client)) {
			return;
		}
		
		list.add(client);
		onlineUsers.put(username, list);
	}
	
	// remove user from online users
	public void removeUser(String username) {
		onlineUsers.remove(username);
	}
	
	// delete socket from a certain list
	public void deleteSocket(String username, ConnectionData socket) {
		try {
			for(ConnectionData connection: onlineUsers.get(username)) {
				if(connection.toString().equals(socket.toString())){
					onlineUsers.get(username).remove(connection);
				}
			}
		}catch(NullPointerException | ConcurrentModificationException e) {
			//Do nothing
		}finally {
			if(onlineUsers.get(username).isEmpty()) {
				removeUser(username);
			}
		}
	}
	
	// get sockets list from a certain user
	public List<ConnectionData> getConnections(String username){
		return onlineUsers.get(username);
	}
	
	public void lock() {
		while(isUsing.get());
		isUsing.set(true);
	}
	
	public void unlock() {
		isUsing.set(false);
	}
	
	public String toString() {
		StringBuilder listAsAString = new StringBuilder();
		for(Entry<String, ArrayList<ConnectionData>> entry : onlineUsers.entrySet()) {
			listAsAString.append(entry.getKey() + ":\n");
			for(ConnectionData connection : entry.getValue()) {
				String port = String.valueOf(connection.getPort());
				listAsAString.append("\t" + connection.getIP() + ":" + port + "\n");
			}
		}
		
		return listAsAString.toString();
	}
	
}
