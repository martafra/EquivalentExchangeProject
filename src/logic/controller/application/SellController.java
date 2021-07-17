package logic.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.bean.ItemInSaleBean;
import logic.bean.OrderBean;
import logic.bean.OrderReviewBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.dao.ItemInSaleDAO;
import logic.dao.OrderDAO;
import logic.dao.RequestDAO;
import logic.dao.UserDAO;
import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.Request;
import logic.entity.User;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.interfaces.SaleController;
import logic.support.other.Notification;

public class SellController implements SaleController{
	
	private static final String STATUS_STRING = "status";
	
	public List<ItemInSaleBean> getItemList(UserBean userBean){
		String username = userBean.getUserID();
		ItemInSaleDAO itemSaleDAO = new ItemInSaleDAO();
		ArrayList<ItemInSale> items = (ArrayList<ItemInSale>) itemSaleDAO.selectItemsInSaleByUser(username);
		ArrayList<ItemInSaleBean> itemBeans = new ArrayList<>();
		for(ItemInSale item: items) {
			if(Boolean.TRUE.equals(item.getAvailability())) {
				ItemInSaleBean itemBean = new ItemInSaleBean();
				itemBean.setItemID(item.getItemInSaleID());
				itemBean.setItemName(item.getReferredItem().getName());
				itemBean.setMediaPath(item.getMedia().get(0));
				itemBean.setPrice(item.getPrice());
				itemBean.setAvailability(item.getAvailability());
				itemBeans.add(itemBean);
			}
		}
		return itemBeans;
	}
	
	public List<RequestBean> getRequestList(UserBean userBean){
		String username = userBean.getUserID();
		RequestDAO requestDAO = new RequestDAO();
		List<Request> requests = requestDAO.selectAllRequests(username);
		ArrayList<RequestBean> requestBeans = new ArrayList<>();
		for(Request request: requests) {
			RequestBean requestBean = new RequestBean();
			ItemInSale item = request.getReferredItem();
			ItemInSaleBean itemBean = new ItemInSaleBean();
			itemBean.setItemID(item.getItemInSaleID());
			itemBean.setItemName(item.getReferredItem().getName());
			itemBean.setMediaPath(item.getMedia().get(0));
			requestBean.setBuyer(request.getBuyer().getUsername());
			requestBean.setNote(request.getNote());
			requestBean.setReferredItemBean(itemBean);

			requestBeans.add(requestBean);
		}
		return requestBeans;
	}
	
	
	public void acceptRequest(RequestBean requestBean) {
		Notification rejectedRequest = new Notification();
		
		Integer item = requestBean.getReferredItem();
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
		String seller = itemDAO.selectItemInSale(item).getSeller().getUsername();
		String itemID = item.toString();
		
		rejectedRequest.setSender(seller);
		rejectedRequest.setDate(new Date());
		rejectedRequest.setType(NotificationType.REQUEST);
		rejectedRequest.addParameter(STATUS_STRING, "accepted");
		rejectedRequest.addParameter("item", itemID);

		UserDAO userDAO = new UserDAO();
		User buyer = userDAO.selectUser(requestBean.getBuyer());
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(buyer.getUsername(), rejectedRequest);
		
		RequestDAO requestDAO = new RequestDAO();
		requestDAO.deleteRequest(buyer.getUsername(), item);
		
		ItemInSale involvedItem = itemDAO.selectItemInSale(item);
		involvedItem.setAvailability(false);
		itemDAO.updateItemInSale(involvedItem);

		Order order = new Order(buyer, involvedItem);
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.insertOrder(order);
		

		
	}
	
	public void rejectRequest(RequestBean requestBean) {
		Notification rejectedRequest = new Notification();
		
		Integer item = requestBean.getReferredItem();
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
		String seller = itemDAO.selectItemInSale(item).getSeller().getUsername();
		String itemID = item.toString();
		
		rejectedRequest.setSender(seller);
		rejectedRequest.setDate(new Date());
		rejectedRequest.setType(NotificationType.REQUEST);
		rejectedRequest.addParameter(STATUS_STRING, "rejected");
		rejectedRequest.addParameter("item", itemID);
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(requestBean.getBuyer(), rejectedRequest);
		
		RequestDAO requestDAO = new RequestDAO();
		requestDAO.deleteRequest(requestBean.getBuyer(), item);
		
	}
	
	@Override
	public void acceptOrder(OrderBean orderBean) {
		Notification acceptedOrder = new Notification();
		
		Integer orderID = orderBean.getOrderID();
		OrderDAO orderDAO = new OrderDAO();
		
		Order order = orderDAO.selectOrder(orderID);
		String seller = order.getInvolvedItem().getSeller().getUsername();
		Integer itemID = order.getInvolvedItem().getItemInSaleID();
		
		acceptedOrder.setSender(seller);
		acceptedOrder.setDate(new Date());
		acceptedOrder.setType(NotificationType.ORDER);
		acceptedOrder.addParameter(STATUS_STRING, "accepted");
		acceptedOrder.addParameter("item", itemID.toString());
		
		order.setSellerStatus(true);
		
		if(Boolean.TRUE.equals(order.isAccepted())){
			order.setStartDate(new Date());
		}
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getBuyer().getUsername(), acceptedOrder);

		orderDAO.updateOder(order);
		
		
	}
	
	@Override
	public void rejectOrder(OrderBean orderBean) {
		Notification rejectedOrder = new Notification();
		
		Integer orderID = orderBean.getOrderID();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		String seller = order.getInvolvedItem().getSeller().getUsername();
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
		ItemInSale item = order.getInvolvedItem();
		Integer itemID = item.getItemInSaleID();
		
		rejectedOrder.setSender(seller);
		rejectedOrder.setDate(new Date());
		rejectedOrder.setType(NotificationType.ORDER);
		rejectedOrder.addParameter(STATUS_STRING, "rejected");
		rejectedOrder.addParameter("item", itemID.toString());
		
		item.setAvailability(true);
		itemDAO.updateItemInSale(item);
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getBuyer().getUsername(), rejectedOrder);
		
		orderDAO.deleteOrder(orderID);
		
		if (Boolean.TRUE.equals(order.getBuyerStatus())) {
			User buyer = order.getBuyer();
			Integer price = order.getInvolvedItem().getPrice();
			
			buyer.increaseCredit(price);
			UserDAO userDAO = new UserDAO();
			userDAO.updateUser(buyer);
		}
	}
	
	public OrderBean generateOrderSummary(Integer orderID) {
		
		OrderBean orderSummary = new OrderBean();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		User buyer = order.getBuyer();
		ItemInSale item = order.getInvolvedItem();
		UserBean buyerData = new UserBean();
		ItemInSaleBean itemData = new ItemInSaleBean();
		OrderReviewBean reviewBean = new OrderReviewBean();
		
		buyerData.setUserID(buyer.getUsername());
		itemData.setItemID(item.getItemInSaleID());
		itemData.setItemName(item.getReferredItem().getName());
		itemData.setPrice(item.getPrice());
		itemData.setMediaPath(item.getMedia().get(0));
		orderSummary.setBuyer(buyerData);
		orderSummary.setOrderID(orderID);
		orderSummary.setInvolvedItem(itemData);
		orderSummary.setBuyerStatus(order.getBuyerStatus());
		orderSummary.setSellerStatus(order.getSellerStatus());
		orderSummary.setStartDate(order.getStartDate());
		orderSummary.setOrderDate(order.getOrderDate());
		orderSummary.setCode(order.getCode());
		if (order.getOrderReview() != null) {
			reviewBean.setOrderID(order.getOrderID());
			reviewBean.setSellerReliability(order.getOrderReview().getSellerReliability());
			reviewBean.setSellerAvailability(order.getOrderReview().getSellerAvailability());
			reviewBean.setItemCondition(order.getOrderReview().getItemCondition());
			reviewBean.setBuyerNote(order.getOrderReview().getBuyerNote());
		}
		else {
			reviewBean = null;
		}
		orderSummary.setReview(reviewBean);
		return orderSummary;
	}
	
	public Boolean verifyPaymentCode(OrderBean orderBean){
		String code = orderBean.getCode();
		OrderDAO orderDAO = new OrderDAO();
		Integer orderID = orderBean.getOrderID();
		Order order = orderDAO.selectOrder(orderID);
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
		ItemInSale item = itemDAO.selectItemInSale(order.getInvolvedItem().getItemInSaleID());
		
		String sellerID = item.getSeller().getUsername();
		UserDAO sellerDAO = new UserDAO();
		User seller = sellerDAO.selectUser(sellerID);
		
		Notification enteredCode = new Notification();
		MessageSender sender = new MessageSender();
					
		if(code.equals(order.getCode())){
			order.setOrderDate(new Date());
			orderDAO.updateOder(order);
			
			item.setAvailability(false);
			itemDAO.updateItemInSale(item);
			seller.increaseCredit(item.getPrice());
			sellerDAO.updateUser(seller);
			
			enteredCode.setSender(sellerID);
			enteredCode.setDate(new Date());
			enteredCode.setType(NotificationType.ORDER);
			enteredCode.addParameter("code", "valid");
			enteredCode.addParameter("orderID", order.getOrderID().toString());
			sender.sendNotification(order.getBuyer().getUsername(), enteredCode);
			return true;
		}
		else
			return false;
	}
	
	public void removeProduct(ItemInSaleBean itemBean){
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
		if(itemBean.getAvailability()) {
			itemDAO.deleteItemInSale(itemBean.getItemID());}
	}
	

	
}
