package logic.entity;

public class ItemInSale {
	
	private Integer price;
	private String description;
	private Boolean availability;
	/*private Condition */
	//private 		media;
	//private Location address;
	private Item referedItem;
	private User seller;
	
	/*public ItemInSale(Condition condition) {
		
	}*/
	
	public Integer getPrice() {
		return this.price;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Boolean getAvailability() {
		return this.availability;
	}
	
	
	public Item getReferedItem() {
		return this.referedItem;
	}
	
	public User getSeller() {
		return this.seller;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	
	public void setReferedItem(Item referedItem) {
		this.referedItem = referedItem;
	}
	
	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	
}
