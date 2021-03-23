package logic.entity;

public class Book {
	private String author;
	private Integer edition;
	private Integer numberOfPages;
	////private enum bookGenre {NEW, PRATICALLYNEW,VERYGOOD, GOOD, ACCEPTABLE }
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
	
	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}
	


}
