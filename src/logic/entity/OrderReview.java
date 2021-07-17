package logic.entity;

public class OrderReview {
	
	private Integer sellerReliability;
	private Integer sellerAvailability;
	private Integer itemCondition;
	private String buyerNote;
	private static final Double RELIABILITY_WEIGHT = 0.41;
	private static final Double AVAILABILITY_WEIGHT = 0.24;
	private static final Double CONDITION_WEIGHT = 0.35;
	
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
	public Integer getSellerVote() {

		return (int) (sellerReliability * RELIABILITY_WEIGHT + 
				sellerAvailability * AVAILABILITY_WEIGHT +
				itemCondition * CONDITION_WEIGHT);
	}
}
