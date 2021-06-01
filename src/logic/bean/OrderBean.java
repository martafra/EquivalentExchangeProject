package logic.bean;

import java.util.Date;

import logic.entity.ItemInSale;
import logic.support.interfaces.Bean;

public class OrderBean implements Bean{
	private String code;
	private ItemInSaleBean involvedItem;
	private Date orderDate;
	private Integer orderID;
	private Boolean orderStatus;
	private UserBean buyer;
	
	public String getCode() {
		return this.code;
	}
	public Date getOrderDate() {
		return this.orderDate;
	}	
	public ItemInSaleBean getinvolvedItem() {
		return this.involvedItem;
	}
	public Integer getOrderID() {
		return this.orderID;
	}
	public Boolean getOrderStatus() {
		return this.orderStatus;
	}
	public UserBean getBuyer() {
		return this.buyer;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setOrderDate(Date date) {
		this.orderDate = date;
	}
	public void setinvolvedItem(ItemInSaleBean item) {
		this.involvedItem = item;
	}
	public void setOrderID(Integer id) {
		this.orderID = id;
	}
	public void setOrderStatus(Boolean status) { 
		this.orderStatus = status;
	}
	public void setBuyer(UserBean user) {
		this.buyer = user;
	}
}
