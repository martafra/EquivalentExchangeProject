package logic.entity;
import java.util.Date;
import java.util.Random;

public class Order {
	
	private String code;
	private ItemInSale involvedItem;
	private Date orderDate;
	private Date startDate; 
	private Integer orderID;
	private Boolean sellerStatus;
	private Boolean buyerStatus;
	private User buyer;
	private OrderReview orderReview;

	public Order(User buyer, ItemInSale involvedItem){
		
		this.buyer = buyer;
		this.involvedItem = involvedItem;
		this.orderID = (buyer.getUsername().hashCode() +
						    involvedItem.hashCode() + new Random().nextInt()) % Integer.MAX_VALUE;

	}
	
	public Order(int orderID, String code, ItemInSale involvedItem, Date orderDate, Date startDate, User buyer, Boolean buyerStatus, Boolean sellerStatus) {
		this.orderID = orderID;
		this.code = code;
		this.involvedItem = involvedItem;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.buyerStatus = buyerStatus;
		this.sellerStatus = sellerStatus;
		this.buyer = buyer;
	}
	
	public String getCode() {
		return this.code;
	}
	public Date getOrderDate() {
		return this.orderDate;
	}
	public Date getStartDate() {
		return this.startDate;
	}
	public ItemInSale getInvolvedItem() {
		return this.involvedItem;
	}
	public Integer getOrderID() {
		return this.orderID;
	}
	public Boolean isAcceptedByBuyer() {
		return this.buyerStatus;
	}
	public Boolean isAcceptedBySeller() {
		return this.sellerStatus;
	}
	public Boolean isAccepted() {
		return this.sellerStatus && this.buyerStatus;
	}
	public User getBuyer() {
		return this.buyer;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public void setOrderDate(Date date) {
		this.orderDate = date;
	}
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	public void setInvolvedItem(ItemInSale item) {
		this.involvedItem = item;
	}
	public void setOrderID(Integer id) {
		this.orderID = id;
	}
	public void setBuyerStatus(Boolean status) { 
		this.buyerStatus = status;
	}
	public void setSellerStatus(Boolean status) { 
		this.sellerStatus = status;
	}
	public void setBuyer(User user) {
		this.buyer = user;
	}

	public Boolean getSellerStatus() {
		return sellerStatus;
	}

	public Boolean getBuyerStatus() {
		return buyerStatus;
	}

	public OrderReview getOrderReview() {
		return orderReview;
	}

	public void setOrderReview(OrderReview orderReview) {
		this.orderReview = orderReview;
	}
	
}
