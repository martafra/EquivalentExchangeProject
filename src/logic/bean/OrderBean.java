package logic.bean;

import java.util.Date;
import logic.support.interfaces.Bean;

public class OrderBean implements Bean{
	private String code;
	private ItemInSaleBean involvedItem;
	private Date orderDate;
	private Date startDate;
	private Integer orderID;
	private Boolean sellerStatus;
	private Boolean buyerStatus;
	private UserBean buyer;
	
	public ItemInSaleBean getInvolvedItem() {
		return involvedItem;
	}
	public void setInvolvedItem(ItemInSaleBean involvedItem) {
		this.involvedItem = involvedItem;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Boolean getSellerStatus() {
		return sellerStatus;
	}
	public void setSellerStatus(Boolean sellerStatus) {
		this.sellerStatus = sellerStatus;
	}
	public Boolean getBuyerStatus() {
		return buyerStatus;
	}
	public void setBuyerStatus(Boolean buyerStatus) {
		this.buyerStatus = buyerStatus;
	}
	public String getCode() {
		return this.code;
	}
	public Date getOrderDate() {
		return this.orderDate;
	}	
	public Integer getOrderID() {
		return this.orderID;
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
	public void setOrderID(Integer id) {
		this.orderID = id;
	}
	public void setBuyer(UserBean user) {
		this.buyer = user;
	}
}
