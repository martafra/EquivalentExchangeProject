package logic.entity;

public class Request {

	private User buyer;
	private ItemInSale referredItem;
	private Boolean requestStatus;
	private String note;

	public Request(User buyer, ItemInSale referredItem, Boolean requestStatus, String note) {
		this.buyer = buyer;
		this.referredItem = referredItem;
		this.requestStatus = requestStatus;
		this.note = note;
	}
	
	public User getBuyer() {
		return this.buyer;
	}
	public User getSeller() {
		return this.referredItem.getSeller();
	}
	public ItemInSale getReferredItem() {
		return this.referredItem;
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
	public void setReferredItem(ItemInSale item) {
		this.referredItem = item;
	}
	public void setStatus(Boolean status) {
		this.requestStatus = status;
	}
	public void setNote(String note) { // inserire controllo sul limite di caratteri
		this.note = note;
	}



}