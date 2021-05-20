package logic.bean;

import java.util.ArrayList;
import logic.enumeration.Condition;
import logic.support.interfaces.Bean;

public class ItemAdBean implements Bean {
	
	private Integer price;
	private String description;
	private Condition condition;
	private ArrayList<String> media;
	private String address; //TODO da modificare? string -> Location
	private Integer referredItemID;
	
	public Integer getReferredItemID() {
		return referredItemID;
	}
	public void setReferredItemID(Integer referredItemID) {
		this.referredItemID = referredItemID;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public ArrayList<String> getMedia() {
		return media;
	}
	public void setMedia(ArrayList<String> media) {
		this.media = media;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
