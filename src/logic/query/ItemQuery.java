package logic.query;

public class ItemQuery {
	private String table = "item";

	private String updateStr = "UPDATE " + table + " SET ";
	//private String selectAll = "SELECT * from " + table;
	private String insertStr = "INSERT INTO " + table;
	
	private String clItemID = "itemID";
	private String clItemName = "itemName";
	private String clPublishingDate = "publisghinDate";
	private String clItemTarget = "itemTarget";
	private String clGenre = "genre";
	private String clPublisher = "publisher";
	private String clItemType = "itemType";
	private String clMovieDuration = "movieDuration";
	private String clBookAuthor = "bookAuthor";
	private String clBookEdition = "bookEdition";
	private String clBookPagesNumber = "bookPagesNumber";
	
	public String selectItem(int itemID) {
		return "SELECT * FROM item WHERE itemID =" + itemID;
	}
	
	public String insertItem(String itemName, char itemType) {
		return insertStr + 
				"(" +  clItemName + ", " + clItemType + ")"
				+ "VALUES ('" + itemName + "','" + itemType + "')";
				
	}
	
	public String insertItem(Integer itemID, String itemName, char itemType) {
		return insertStr + 
				"(" + clItemID + ", " + clItemName + ", " + clItemType + ")"
				+ "VALUES (" +itemID +  ",'" + itemName + "','" + itemType + "')";
				
	}
}
