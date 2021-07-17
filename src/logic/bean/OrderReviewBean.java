package logic.bean;

public class OrderReviewBean {
	
	private Integer sellerReliability;
	private Integer sellerAvailability;
	private Integer itemCondition;
	private String buyerNote;
	private Integer orderID;
	
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
	public Integer getOrderID() {
		return orderID;
	}
	public void setBuyerNote(String buyerNote) {
		this.buyerNote = buyerNote;
	}
	public String getBuyerNote() {
		return buyerNote;
	}
	public void setItemCondition(Integer itemCondition) {
		this.itemCondition = itemCondition;
	}
	public Integer getItemCondition() {
		return itemCondition;
	}
	public void setSellerAvailability(Integer sellerAvailability) {
		this.sellerAvailability = sellerAvailability;
	}
	public Integer getSellerAvailability() {
		return sellerAvailability;
	}
	public void setSellerReliability(Integer sellerReliability) {
		this.sellerReliability = sellerReliability;
	}
	public Integer getSellerReliability() {
		return sellerReliability;
	}
}
