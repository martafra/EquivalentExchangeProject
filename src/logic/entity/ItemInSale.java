package logic.entity;
import logic.enumeration.Condition;

public class ItemInSale {
	
	private Integer price;
	private String description;
	private Boolean availability;
	private Condition condition;
	//private String media;
	private String address; //da modificare? string -> Location
	private Item referredItem;
	private User seller;
	
	public ItemInSale(int price, boolean availability, String preferredLocation, Item referredItem, User seller) {
		this.price = price;
		this.availability = availability;
		this.address = preferredLocation;
		this.referredItem = referredItem;
		this.seller = seller;	
	}
	
	public Integer getPrice() {
		return this.price;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Condition getCondition() {
		return this.condition;
	}
	
	public Boolean getAvailability() {
		return this.availability;
	}
	
	
	public Item getReferredItem() {
		return this.referredItem;
	}
	
	public User getSeller() {
		return this.seller;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCondition(Condition condition) {
		this.condition = condition;;
	}
	
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	
	public void setReferredItem(Item referredItem) {
		this.referredItem = referredItem;
	}
	
	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
