package logic.bean;

import logic.support.interfaces.Bean;

public class ItemInSaleBean implements Bean{
	
	private Integer itemID;
	private Integer price;
	private ItemBean referredItem = new ItemBean();
	private String mediaPath;
	private UserBean seller = new UserBean();

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
		return referredItem.getItemName();
	}
	public void setItemName(String itemName) {
		this.referredItem.setItemName(itemName);;
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

	protected ItemBean getReferredItem(){
		return this.referredItem;
	}

	protected void setReferredItem(ItemBean item){
		referredItem = item;
	}

	
	
}
