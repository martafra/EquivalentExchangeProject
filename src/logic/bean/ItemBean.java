package logic.bean;

import logic.support.interfaces.Bean;

public class ItemBean implements Bean{
	
	private String itemName;
	private int itemID;
	
	public ItemBean(int itemID, String itemName) {
		this.itemName = itemName;
		this.itemID = itemID;
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
	
}
