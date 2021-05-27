package logic.bean;

public class RequestBean {
	
	private String buyer;
	private Integer referredItem;
	private Boolean requestStatus;
	private String note;
	
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public Integer getReferredItem() {
		return referredItem;
	}
	public void setReferredItem(Integer referredItem) {
		this.referredItem = referredItem;
	}
	public Boolean getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(Boolean requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
