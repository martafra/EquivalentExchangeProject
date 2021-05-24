package logic.entity;

import java.util.Date;

public abstract class Item {
	private int itemID;
	private String name;
	private Date publishingDate;
	//private String language;
	//private enum target { }
	
	public int getItemID() {
		return this.itemID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Date getPublishingDate() {
		return this.publishingDate;
	}
		
	/*public String getLanguage() {
		return this.language;
	}*/
	
	public abstract char getType();
	
	public abstract String getInfo();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}
	
//	public void setPublishingDate(String publishingDate) {
//		if (publishingDate!= null) {
//			this.publishingDate = Date.valueOf(publishingDate);
//		}
//	
//	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
		
	/*public void setLanguage(String language) {
		this.language = language;
	}*/
	
	
	
}
