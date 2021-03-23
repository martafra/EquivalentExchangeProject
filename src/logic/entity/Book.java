package logic.entity;
import logic.enumeration.BookGenre;

public class Book extends Item {
	private String author;
	private Integer edition;
	private Integer numberOfPages;
	private BookGenre genre;
	private String  publishingHouse;
	
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
	
	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}
	


}
