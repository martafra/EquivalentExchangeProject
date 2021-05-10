package logic.support.connection;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionCache {

    private static ConnectionCache instance = null;
    private HashMap<String, ArrayList<ConnectionData>> onlineUsers = new HashMap<>();

    private ConnectionCache(){}

    public static ConnectionCache getInstance(){

        if(instance == null){
            instance = new ConnectionCache();
        }

        return instance;
    }
    
    public void addUser(String username, String clientData) {
		ArrayList<ConnectionData> list = onlineUsers.get(username);
		ConnectionData client = new ConnectionData(clientData);
        
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
		return;
	}
	
	// remove user from online users
	public void removeUser(String username) {
		onlineUsers.remove(username);
	}
	
	// delete socket from a certain list
	public void deleteSocket(String username, String clientData) {

        ConnectionData socket = new ConnectionData(clientData);
		try {
			onlineUsers.get(username).remove(socket);
			if(onlineUsers.get(username).isEmpty()) {
				removeUser(username);
			}
		}
		catch(NullPointerException e) {
			
		}
	}
	
	// get connections list from a certain user
	public ArrayList<String> getConnections(String username){

        ArrayList<String> connections = new ArrayList<>();
		for(ConnectionData data : onlineUsers.get(username)){
            connections.add(data.getStringData());
        }

        return connections;
	}
}
