package logic.bean;

import java.util.Date;

import logic.entity.ItemInSale;
import logic.entity.User;

public class OrderBean {
	private String code;
	private ItemInSale involvedItem;
	private Date orderDate;
	private Integer orderID;
	private Boolean orderStatus;
	private User buyer;
	
	public String getCode() {
		return this.code;
	}
	
	public Date getOrderDate() {
		return this.orderDate;
	}
	
	public ItemInSale getinvolvedItem() {
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
	
	public void setinvolvedItem(ItemInSale item) {
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
