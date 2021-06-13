package logic.bean;

import java.util.ArrayList;

import logic.support.interfaces.Bean;

public class ItemDetailsBean implements Bean {
	private Integer itemInSaleID;
	private Integer price;
	private String description;
	private String condition;
	private ArrayList<String> media = new ArrayList<>();
	private String address; //TODO da modificare? string -> Location
	private Integer referredItemID;
	private String sellerID;
	
	
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
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
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
	public void addMedia(String selectedImagePath) {	
		this.media.add(selectedImagePath);
	}
	public String getSellerID() {
		return sellerID;
	}
	public void setSellerID(String seller) {
		this.sellerID = seller;
	}
	public Integer getItemInSaleID() {
		return itemInSaleID;
	}
	public void setItemInSaleID(Integer itemInSaleID) {
		this.itemInSaleID = itemInSaleID;
	}
	

}
