package logic.query;

import java.text.SimpleDateFormat;

import logic.entity.Item;

public class ItemQuery {
	private String table = "item";

	private String updateStr = "UPDATE " + table + " SET ";
	//private String selectAll = "SELECT * from " + table;
	private String insertStr = "INSERT INTO " + table;
	
	private String clItemID = "itemID";
	private String clItemName = "itemName";
	private String clPublishingDate = "publishingDate";
	private String clItemTarget = "itemTarget";
	private String clGenre = "genre";
	private String clPublisher = "publisher";
	private String clItemType = "itemType";
	private String clMovieDuration = "movieDuration";
	private String clBookAuthor = "bookAuthor";
	private String clBookEdition = "bookEdition";
	private String clBookPagesNumber = "bookPagesNumber";
	
	private String columnsName = " (" + clItemID + ", "   + clItemName + ", " + clPublishingDate + ", "  + /*clItemTarget+ ", " +*/
			clGenre+ ", " +   clPublisher + ", " +   clItemType + ", " +  clMovieDuration + ", " + clBookAuthor + ", " + 
			clBookEdition + ", " + clBookPagesNumber +") ";
	
	private String columnsNameBook = " (" + clItemID + ", " + clItemName + ", " + clPublishingDate + ", "  + /*clItemTarget + ", "  +*/
			clGenre+ ", " +   clPublisher + ", " +   clItemType + ", " + clBookAuthor + ", " + 
			clBookEdition + ", " + clBookPagesNumber +") ";
	
	private String columnsNameMovie = " (" + clItemName + ", " + clPublishingDate + ", " + /* clItemTarget + ", " +*/
			clGenre+ ", " +   clItemType + ", " +  clMovieDuration + ") ";
	
	private String columnsNameVideoGame = " (" + clItemName + ", " + clPublishingDate + ", "  + /*clItemTarget+ ", " +*/
			clGenre+ ", "  +   clItemType + ") ";
	
	public String selectItem(int itemID) {
		return "SELECT * FROM item WHERE itemID =" + itemID;
	}
	
	public String getAllItems() {
		return "SELECT * FROM item";
	}
	
	public String changeStr(String str) {
		if(str !=null) {
			return "'" + str + "'";
		}
		return str;
	}
	
	public String insertItem(Item item) {
		String query = insertStr;
		String info = item.getInfo();
		String [] infoArray = info.split(";");
		String itemName = item.getName();
		String publishingDate = new SimpleDateFormat("yyyy-mm-dd").format(item.getPublishingDate());
		char itemType =  item.getType() ;
		String str;
		
		
		if (itemType == 'B') {
			
			Integer itemID = item.getItemID();
			String author = infoArray[0];
			String edition = infoArray[1];
			Integer pages = Integer.valueOf(infoArray[2]);
			String bookGenre = infoArray[3];
			String publishingHouse = infoArray[4];
			str= String.format(query + columnsNameBook + " VALUES (%d, \"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d)", itemID, itemName, publishingDate ,
					bookGenre, publishingHouse ,itemType , author, edition , pages);
			
			return str;
		}
		else if (itemType == 'M') {
			String duration = infoArray[0];
			String movieGenre = infoArray[1];
			str= String.format(query + columnsNameMovie + " VALUES (%s,%s,%s,%s,%d)", itemName, publishingDate, movieGenre, itemType, duration);
			return str;
		}
		else{
			
			String videogameGenre = infoArray[0];
			str= String.format(query + columnsNameVideoGame + " VALUES (%s,%s,%s,%s)", itemName, publishingDate, videogameGenre, itemType);
			return str;
		}
	}
	
}
