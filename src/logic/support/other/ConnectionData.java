package logic.support.other;

public class ConnectionData {
	
	private String ip;
	private int port;
	
	public ConnectionData(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public ConnectionData(String data){
		String[] parsedData = data.split(":");
		ip = parsedData[0];
		port = Integer.valueOf(parsedData[1]);
	}
	
	public String getIP() {
		return this.ip;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public String getStringData(){
		return ip + ":" + port;
	}

}
