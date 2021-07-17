package logic.support.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import logic.entity.Book;
import logic.entity.Item;
import logic.entity.Videogame;
import logic.entity.Movie;

public class ItemFactory {
	String genreStr = "genre";
	String itemNameStr = "itemName";
	String languageStr = "language";
	String  pubDate = "publishingDate";
	String dateFormat = "yyyy-mm-dd";
	
	public Item makeItem(Map<String, String> data){//controlla la tipologia della classe da istanziare
		Item item = null;
		char itemType = data.get("itemType").charAt(0);
		if (itemType == 'B') { 
			item = makeBook(data);
		}
		else if (itemType == 'V') {
			item = makeVideoGame(data);
		}
		else {
			item = makeMovie(data);
		}
		item.setItemID(Integer.parseInt(data.get("itemID")));
		return item;
	}
	
	private Item makeBook(Map<String, String> data){ //popola e ritorna la classe Book
		String language = data.get(languageStr);
		String author = data.get("bookAuthor");
		String editionStr = data.get("bookEdition");
		Integer edition = null;
		Integer numberOfPages = null;
		if (editionStr != null) {
			edition = Integer.parseInt(editionStr);
		}
		
		String numberOfPageStr = data.get("bookPagesNumber");
		if (numberOfPageStr != null) {
			numberOfPages = Integer.parseInt(numberOfPageStr);
		}

		String genre = data.get(genreStr);
		String publishingHouse = data.get("publisher");
		String name = data.get(itemNameStr);
		Date publishingDate = null;
		try {
			publishingDate = new SimpleDateFormat(dateFormat).parse(data.get(pubDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Book book = new Book(name, publishingDate, author, edition, genre, publishingHouse, language);
		book.setNumberOfPages(numberOfPages);
		return book;
	}
	
	private Item makeVideoGame(Map<String, String> data){ //popola e ritorna la classe VideoGame, per ora non è presente VGConsole perché devo capire come gestirlo
		String name = data.get(itemNameStr);
		String genre = data.get(genreStr);
		String language = data.get(languageStr);
		String console = data.get("console");
		Date publishingDate = null;
		try {
			publishingDate = new SimpleDateFormat(dateFormat).parse(data.get(pubDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Videogame(name, publishingDate, genre, console, language);
	}
	
	private Item makeMovie(Map<String, String> data){ //popola e ritorna la classe Movie
		String language = data.get(languageStr);
		String durationStr = data.get("movieDuration");
		String name = data.get(itemNameStr);
		Date publishingDate = null;
		try {
			publishingDate = new SimpleDateFormat(dateFormat).parse(data.get(pubDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer duration = null;
		if (durationStr != null) {
			duration = Integer.parseInt(durationStr);
		}
		String genre = data.get(genreStr);
		return new Movie(name, publishingDate, duration, genre, language);
	}

}
