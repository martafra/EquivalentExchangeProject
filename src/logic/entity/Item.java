package logic.entity;
import java.util.*;

public class Item {
	private String name;
	private Date publishingDate;
	private String language;
	//private enum target { }
	
	public String getName() {
		return this.name;
	}
	
	public Date getPublishingDate() {
		return this.publishingDate;
	}
		
	public String getLanguage() {
		return this.language;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void getPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}
		
	public void getLanguage(String language) {
		this.language = language;
	}
	
	
}
