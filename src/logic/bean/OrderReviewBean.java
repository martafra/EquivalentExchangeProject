package logic.bean;

public class OrderReviewBean {
	
	private Integer sellerrReliability;
	private Integer sellerAvailability;
	private Integer itemCondition;
	private String buyerNote;
	private Integer orderID;
	
	public Integer getSellerrReliability() {
		return sellerrReliability;
	}
	public void setSellerrReliability(Integer sellerrReliability) {
		this.sellerrReliability = sellerrReliability;
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
