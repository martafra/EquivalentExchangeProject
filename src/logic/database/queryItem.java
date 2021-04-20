package logic.database;

import java.sql.Date;
import java.sql.ResultSet;


public class queryItem {
MyDB myDB;
	
	public queryItem() {
		myDB = new MyDB();
		myDB.connect();	
	}
	
	
	/*public void insertItem(int itemID, String itemName, Date publishingDate, int itemTarget, String genre, String publisher, char itemType, int moovieDuration, String bookAuthor, int bookEdition, int bookPages) {
		myDB.doUpdate("INSERT INTO item values('"+ itemID + "','" + itemName + "','" + publishingDate + "','" + itemTarget + "','" + genre + "','" + publisher + "','" + itemType + "','" + moovieDuration + "','" +bookAuthor + "','" + bookEdition + "','" + bookPages + "')");	
	}*/
	
	public void selectItem(int IDitem) {
		ResultSet rs = myDB.makeQuery("SELECT * FROM item WHERE itemID =" + IDitem);
		//da finire
		myDB.showAll(rs);	
	}
	


	
}
