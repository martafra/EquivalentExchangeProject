package logic.bean;

import logic.support.interfaces.Bean;

public class ItemInSaleBean implements Bean{
	
	Integer price;
	Integer itemID;
	String itemName;
	String mediaPath;
	UserBean seller;
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getItemID() {
		return itemID;
	}
	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getMediaPath() {
		return mediaPath;
	}
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}
	public UserBean getSeller() {
		return seller;
	}
	public void setSeller(UserBean seller) {
		this.seller = seller;
	}
	
	
}
