package logic.entity;


import java.util.Date;

import logic.enumeration.BookGenre;


public class Book extends Item {
	private String author;
	private Integer edition;
	private Integer numberOfPages;
	private BookGenre genre;
	private String  publishingHouse;
	
	public Book (String name, Date publishingDate, String author, Integer edition, Integer numberOfPages, String genre, String publishingHouse) {
		this.setName(name);
		this.setPublishingDate(publishingDate);
		this.author = author;
		this.edition = edition;
		this.numberOfPages = numberOfPages;
		setGenre(genre);
		this.publishingHouse = publishingHouse;
		
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public Integer getEdtion() {
		return this.edition;
	}
	
	public Integer getNumberOfPages() {
		return this.numberOfPages;
	}
	
	public BookGenre getGenre() {
		return this.genre;
	}
	
	public String getPublishingHouse() {
		return this.publishingHouse;
	}
	
	public char getType() {
		return 'B';
	}
	
	public String getInfo() {
		return author + ";" + edition + ";" + numberOfPages + ";" + genre + ";" + publishingHouse;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setEdition(Integer edition) {
		this.edition = edition;
	}
	
	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}
	
	public void setGenre(String genre) {
		if(genre != null) {
			for (BookGenre value : BookGenre.values()) {
				if (genre.equals(value.toString())){
					  this.genre = value;
					  return;
				}
			}
		}
		this.genre = null;
	}
	
	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}
	


}
