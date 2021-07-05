package logic.bean;

import java.util.Date;

import logic.support.interfaces.Bean;

public class ItemBean implements Bean{
	
	private String itemName;
	private int itemID;
	private Date publishingDate;
	private char type;
	private String language;
	//private enum target { }
	private String author;
	private Integer edition;
	private Integer numberOfPages;
	private String genre;
	private String  publishingHouse;
	private Integer duration;
	private String console;
	
	public ItemBean(int itemID, String itemName) {
		this.itemName = itemName;
		this.itemID = itemID;
	}
	
	public ItemBean() {
		
	}
	public int getItemID(){
		return itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Date getPublishingDate() {
		return this.publishingDate;
	}
	
	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}
	
	public void setType(char type) {
		this.type = type;
	}
	public char getType() {
		return this.type;	
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Integer getEdtion() {
		return this.edition;
	}
	
	public void setEdition(Integer edition) {
		this.edition = edition;
	}
	
	public Integer getNumberOfPages() {
		return this.numberOfPages;
	}
	
	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}	
	
	public String getConsole() {
		return this.console;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}	
	
	public String getLanguage() {
		return this.language;
	}
	
	public void setConsole(String console) {
		this.console = console;
	}	
	
	public String getPublishingHouse() {
		return this.publishingHouse;
	}
	
	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}
	
	public Integer getDuration() {
		return this.duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@Override
	public String toString() {
		return this.itemName;
	}
}
