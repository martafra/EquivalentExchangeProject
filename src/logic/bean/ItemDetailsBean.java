package logic.bean;

import java.util.ArrayList;
import java.util.List;

import logic.support.interfaces.Bean;

public class ItemDetailsBean extends ItemInSaleBean{
	
	private String description;
	private String condition;
	private ArrayList<String> media = new ArrayList<>();
	private String address; //TODO da modificare? string -> Location
	
	public Integer getReferredItemID() {
		return getReferredItem().getItemID();
	}
	public void setReferredItemID(Integer referredItemID) {
		if(this.getReferredItem() == null){
			setReferredItem(new ItemBean());
		}
		this.getReferredItem().setItemID(referredItemID);
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
	public List<String> getMedia() {
		return media;
	}
	public void setMedia(List<String> media) {
		if(!media.isEmpty())
			this.setMediaPath(media.get(0));
		this.media = (ArrayList<String>) media;
	}
	public void addMedia(String selectedImagePath) {	
		this.media.add(selectedImagePath);
	}
	public void removeMedia(String selectedImagePath) {
		this.media.remove(selectedImagePath);
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getItemInSaleID() {
		return this.getItemID();
	}
	public void setItemInSaleID(Integer itemInSaleID) {
		this.setItemID(itemInSaleID);
	}
	

}
