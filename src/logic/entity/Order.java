package logic.entity;
import java.util.Date;

public class Order {
	
	private String code;
	private ItemInSale involvedItem;
	private Date orderDate;
	private Date startDate; 
	private Integer orderID;
	private Boolean orderStatus;
	private User buyer;
	
	public Order(int orderID, String code, ItemInSale involvedItem, Date orderDate, Date startDate, Boolean orderStatus, User buyer) {
		this.orderID = orderID;
		this.code = code;
		this.involvedItem = involvedItem;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.orderStatus = orderStatus;
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
	public Boolean getOrderStatus() {
		return this.orderStatus;
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
	public void setOrderStatus(Boolean status) { 
		this.orderStatus = status;
	}
	public void setBuyer(User user) {
		this.buyer = user;
	}

}
