package logic.entity;

public class Request {
	
	private User buyer;
	private ItemInSale referedItem;
	private Boolean requestStatus;
	private String note;
	
	public User getBuyer() {
		return this.buyer;
	}
	public User getSeller() {
		return this.referedItem.getSeller();
	}
	public ItemInSale getReferedItem() {
		return this.referedItem;
	}
	public Boolean getStatus() {
		return this.requestStatus;
	}
	public String getNote() {
		return this.note;
	}
	
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public void setReferedItem(ItemInSale item) {
		this.referedItem = item;
	}
	public void setStatus(Boolean status) {
		this.requestStatus = status;
	}
	public void setNote(String note) { // inserire controllo sul limite di caratteri
		this.note = note;
	}
	
	

}
