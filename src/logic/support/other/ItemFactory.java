package logic.support.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import logic.entity.Book;
import logic.entity.Item;
import logic.entity.Videogame;
import logic.entity.Movie;
import logic.enumeration.BookGenre;
import logic.enumeration.MovieGenre;
import logic.enumeration.VideoGameGenre;

public class ItemFactory {
	//
	public Item makeItem(HashMap<String, String> data){//controlla la tipologia della classe da istanziare
		Item item = null;
		char itemType = data.get("itemType").charAt(0);
		if (itemType == 'B') { 
			item = makeBook(data);
		}
		else if (itemType == 'V') {
			item = makeVideoGame(data);
		}
		else { //if (itemType == 'M') {
			item = makeMovie(data);
		}
		item.setItemID(Integer.parseInt(data.get("itemID")));
		return item;
	}
	
	private Item makeBook(HashMap<String, String> data){ //popola e ritorna la classe Book
		String author = data.get("bookAuthor");
		//TODO fare il controllo
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

		String genre = data.get("genre");
		String publishingHouse = data.get("publisher");
		String name = data.get("itemName");
		Date publishingDate = null;
		try {
			publishingDate = new SimpleDateFormat("yyyy-mm-dd").parse(data.get("publishingDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Book(name, publishingDate, author, edition, numberOfPages, genre, publishingHouse);
		
	}
	
	private Item makeVideoGame(HashMap<String, String> data){ //popola e ritorna la classe VideoGame, per ora non è presente VGConsole perché devo capire come gestirlo
		String genre = data.get("genre");
		String name = data.get("itemName");
		String console = data.get("console");
		Date publishingDate = null;
		try {
			publishingDate = new SimpleDateFormat("yyyy-mm-dd").parse(data.get("publishingDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Videogame(name, publishingDate, genre, console);
	}
	
	private Item makeMovie(HashMap<String, String> data){ //popola e ritorna la classe Movie
		String durationStr = data.get("movieDuration");
		String name = data.get("itemName");
		Date publishingDate = null;
		try {
			publishingDate = new SimpleDateFormat("yyyy-mm-dd").parse(data.get("publishingDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer duration = null;
		if (durationStr != null) {
			duration = Integer.parseInt(durationStr);
		}
		String genre = data.get("genre");
		return new Movie(name, publishingDate, duration, genre);
	}

}
