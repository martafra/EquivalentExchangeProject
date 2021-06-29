package logic.bean;

public class OrderReviewBean {
	
	private Integer sellerReliability;
	private Integer sellerAvailability;
	private Integer itemCondition;
	private String buyerNote;
	private Integer orderID;
	
	
	public Integer getSellerReliability() {
		return sellerReliability;
	}
	public void setSellerReliability(Integer sellerReliability) {
		this.sellerReliability = sellerReliability;
	}
	public Integer getSellerAvailability() {
		return sellerAvailability;
	}
	public void setSellerAvailability(Integer sellerAvailability) {
		this.sellerAvailability = sellerAvailability;
	}
	public Integer getItemCondition() {
		return itemCondition;
	}
	public void setItemCondition(Integer itemCondition) {
		this.itemCondition = itemCondition;
	}
	public String getBuyerNote() {
		return buyerNote;
	}
	public void setBuyerNote(String buyerNote) {
		this.buyerNote = buyerNote;
	}
	public Integer getOrderID() {
		return orderID;
	}
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
}
