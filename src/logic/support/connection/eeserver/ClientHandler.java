package logic.support.connection.eeserver;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import logic.support.connection.ConnectionData;




public class ClientHandler implements Runnable {
	
	private static final String USERNAME = "username";
	private ConnectionHandler handler;
	private Socket client;

	public ClientHandler(ConnectionHandler handler, Socket client) {
		this.handler = handler;
		this.client = client;
	}
	
	@Override
	public void run() {
		
		
		
		
		try(PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			
	
			String request = reader.readLine();
			HashMap<String, String> requestFields = parseRequestFields(request);
			List<ConnectionData> list;

			handler.lock();
			
			if(requestFields != null) {
				switch(requestFields.get("request")){
					case "get":
						list = handler.getConnections(requestFields.get(USERNAME));
	
						String responseString = "response:get;username:"+requestFields.get(USERNAME)+";data:{";
						StringBuilder response = new StringBuilder();
						response.append(responseString);
						if(list == null || list.isEmpty()) {
							response.append("null}");
							responseString = response.toString();
						}
						else {
							for(ConnectionData data : list){
								response.append(data.getIP()+"-"+data.getPort()+"_");
							}
							responseString = response.toString();
							responseString = responseString.substring(0, responseString.length() - 1);
							responseString+="}";
						}
						writer.println(responseString);
						break;
					case "remove":
						String ip = requestFields.get("ip");
						Integer port = Integer.valueOf(requestFields.get("port"));
						ConnectionData socket = new ConnectionData(ip, port);
						handler.deleteSocket(requestFields.get(USERNAME), socket);
						break;
					case "delete":
						break;
					case "add":
						handler.addUser(requestFields.get(USERNAME), 
										new ConnectionData(requestFields.get("ip"), 
										Integer.parseInt(requestFields.get("port"))));
						break;
					default:
						break;
				}
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			handler.unlock();
		}
	}

	private HashMap<String, String> parseRequestFields(String request){
		if("".equals(request) || request == null){
			return null;
		}
		String[] params = request.split(";");
		HashMap<String, String> requestFields = new HashMap<>();

		for(String param : params){
			String[] keyValue = param.split(":");
			requestFields.put(keyValue[0], keyValue[1]);
		}
		return requestFields;
	}


}
