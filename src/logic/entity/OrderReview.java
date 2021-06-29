package logic.entity;

public class OrderReview {
	
	private Integer sellerReliability;
	private Integer sellerAvailability;
	private Integer itemCondition;
	private String buyerNote;
	private final static Double RELIABILITY_WEIGHT = 1.75;
	private final static Double AVAILABILITY_WEIGHT = 1.5;
	private final static Double CONDITION_WEIGHT = 1.0;
	
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
		return (int) ((sellerReliability / RELIABILITY_WEIGHT + 
				sellerAvailability / AVAILABILITY_WEIGHT +
				itemCondition / CONDITION_WEIGHT) / (RELIABILITY_WEIGHT + AVAILABILITY_WEIGHT + CONDITION_WEIGHT));
	}
}
