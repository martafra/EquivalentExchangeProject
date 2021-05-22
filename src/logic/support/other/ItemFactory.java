package logic.support.other;

import java.util.ArrayList;


import logic.entity.Book;
import logic.entity.Item;
import logic.entity.Videogame;
import logic.entity.Movie;
import logic.enumeration.BookGenre;
import logic.enumeration.MovieGenre;
import logic.enumeration.VideoGameGenre;

public class ItemFactory {
	//
	public Item makeItem(char itemType, ArrayList<String> data){//controlla la tipologia della classe da istanziare
		Item item = null;
		if (itemType == 'B') { 
			item = makeBook(data);
		}
		else if (itemType == 'V') {
			item = makeVideoGame(data);
		}
		else { //if (itemType == 'M') {
			item = makeMovie(data);
		}
		item.setItemID(Integer.parseInt(data.get(0)));
		return item;
	}
	
	private Item makeBook(ArrayList<String> data){ //popola e ritorna la classe Book
		String author = data.get(8);
		//TODO fare il controllo
		String editionStr = data.get(9);
		Integer edition = null;
		Integer numberOfPages = null;
		if (editionStr != null) {
			edition = Integer.parseInt(editionStr);
		}
		
		String numberOfPageStr = data.get(10);
		if (numberOfPageStr != null) {
			numberOfPages = Integer.parseInt(numberOfPageStr);
		}

		String genre = data.get(4);
		String publishingHouse = data.get(5);
		String name = data.get(1);
		String publishingDate = data.get(2);
		
		return new Book(name, publishingDate, author, edition, numberOfPages, genre, publishingHouse);
		
	}
	
	private Item makeVideoGame(ArrayList<String> data){ //popola e ritorna la classe VideoGame, per ora non è presente VGConsole perché devo capire come gestirlo
		String genre = data.get(4);
		String name = data.get(1);
		String publishingDate = data.get(2);
		return new Videogame(name,publishingDate,genre);
	}
	
	private Item makeMovie(ArrayList<String> data){ //popola e ritorna la classe Movie
		String durationStr = data.get(7);
		String name = data.get(1);
		String publishingDate = data.get(2);
		Integer duration = null;
		if (durationStr != null) {
			duration = Integer.parseInt(durationStr);
		}
		String genre = data.get(4);
		return new Movie(name, publishingDate, duration, genre);
	}

}
