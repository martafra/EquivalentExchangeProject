package logic.entity;
import java.sql.Date;



public class Item {
	private String name;
	private Date publishingDate;
	//private String language;
	//private enum target { }
	
	public String getName() {
		return this.name;
	}
	
	public Date getPublishingDate() {
		return this.publishingDate;
	}
		
	/*public String getLanguage() {
		return this.language;
	}*/
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}
	
	public void setPublishingDate(String publishingDate) {
		if (publishingDate!= null) {
			this.publishingDate = Date.valueOf(publishingDate);
		}
	
	}
		
	/*public void setLanguage(String language) {
		this.language = language;
	}*/
	
	
}
