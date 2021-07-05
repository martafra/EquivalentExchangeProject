package logic.entity;

import java.util.Date;

import logic.enumeration.BookGenre;
import logic.enumeration.Language;

public abstract class Item {
	private int itemID;
	private String name;
	private Date publishingDate;
	private Language language;
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
		
	public Language getLanguage() {
		return this.language;
	}
	
	public abstract Character getType();
	
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
		
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public void setLanguage(String language) {
		if(language != null) {
			for (Language value : Language.values()) {
				if (language.equals(value.toString())){
					  this.language = value;
					  return;
				}
			}
		}
		this.language = null;
	}
	
	
}
