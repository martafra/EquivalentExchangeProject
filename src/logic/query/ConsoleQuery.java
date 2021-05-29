package logic.query;

public class ConsoleQuery extends Query{

	public String insertConsole(Integer itemID, String consoleName) {
		consoleName = quote(consoleName);
		String query = "INSERT INTO console (itemID, console) VALUES (%d, %s);";
		return String.format(query, itemID, consoleName);
	}
	
	public String selectConsoles(Integer itemID) {
		String query = "SELECT console from Console WHERE itemID = %d;";
		return String.format(query, itemID);
	}

}
