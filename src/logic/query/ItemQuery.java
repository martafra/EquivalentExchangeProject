package logic.query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemQuery extends Query{
	
	public String selectItem(int itemID) {
		String query = "SELECT * FROM item WHERE itemID = %d;";
		return String.format(query, itemID);
	}
	
	public String getAllItems() {
		return "SELECT *" +
			   "FROM Item";
	}

	public String insertItem(Integer itemID, String itemName, Date publishingDate, String publisher, String language) {
		
		DateFormat format = new SimpleDateFormat(dateFormat);
		
		itemName = quote(itemName);
		String publishingDateString = format.format(publishingDate);
		publishingDateString = quote(publishingDateString);
		publisher = quote(publisher);
		language = quote(language);
		
		String query = "INSERT INTO Item (itemID, itemName, publishingDate, publisher, language) "+
					   "VALUES (%d, %s, %s, %s, %s);";
		return String.format(query, itemID, itemName, publishingDateString, publisher, language);
	}
	
	public String insertBookData(Integer itemID, String author, Integer edition, Integer pageNumber, String genre) {
		author = quote(author);
		genre = quote(genre);
		String query = "UPDATE item SET " +
					   "itemType = 'B', " +
					   "bookAuthor = %s, " +
				       "bookEdition = %d, " +
					   "bookPagesNumber = %d," +
					   " genre = %s " +
				       "WHERE ItemID = %d;";
		return String.format(query, author, edition, pageNumber, genre, itemID);
	}
	public String insertMovieData(Integer itemID, Integer movieDuration, String genre) {
		genre = quote(genre);
		String query = "UPDATE Item SET " +
					   "itemType = 'M', " +
					   "movieDuration = %d, " +
					   "genre = %s " +
					   "WHERE itemID = %d;";
		return String.format(query, movieDuration, genre, itemID);
	}
	public String insertVideogameData(Integer itemID, String genre, String console) {
		genre = quote(genre);
		console = quote(console);
		String query = "UPDATE Item SET " +
				       "itemType = 'V', " +
				       "genre = %s , " +
				       "console = %s " +
				       "WHERE itemID = %d;";	
		return String.format(query, genre, console, itemID);
	}
	
	
	
}
