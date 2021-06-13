package logic.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.DAO.ItemInSaleDAO;
import logic.DAO.OrderDAO;
import logic.DAO.RequestDAO;
import logic.DAO.UserDAO;
import logic.bean.ItemInSaleBean;
import logic.bean.OrderBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.Request;
import logic.entity.User;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.Notification;

public class SellController {
	
	public List<ItemInSaleBean> getItemList(UserBean userBean){
		String username = userBean.getUserID();
		ItemInSaleDAO itemSaleDAO = new ItemInSaleDAO();
		ArrayList<ItemInSale> items = (ArrayList<ItemInSale>) itemSaleDAO.selectItemsInSaleByUser(username);
		ArrayList<ItemInSaleBean> itemBeans = new ArrayList<>();
		for(ItemInSale item: items) {
			ItemInSaleBean itemBean = new ItemInSaleBean();
			itemBean.setItemID(item.getItemInSaleID());
			itemBean.setItemName(item.getReferredItem().getName());
			itemBean.setMediaPath(item.getMedia().get(0));
			itemBean.setPrice(item.getPrice());
			itemBeans.add(itemBean);
		}
		return itemBeans;
	}
	
	public List<RequestBean> getRequestList(UserBean userBean){
		String username = userBean.getUserID();
		RequestDAO requestDAO = new RequestDAO();
		ArrayList<Request> requests = (ArrayList<Request>) requestDAO.selectAllRequests(username);
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
		rejectedRequest.addParameter("status", "accepted");
		rejectedRequest.addParameter("item", itemID);

		UserDAO userDAO = new UserDAO();
		User buyer = userDAO.selectUser(requestBean.getBuyer());
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(buyer.getUsername(), rejectedRequest);
		
		RequestDAO requestDAO = new RequestDAO();
		requestDAO.deleteRequest(buyer.getUsername(), item);
		
		ItemInSale involvedItem = itemDAO.selectItemInSale(item);

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
		rejectedRequest.addParameter("status", "rejected");
		rejectedRequest.addParameter("item", itemID);
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(requestBean.getBuyer(), rejectedRequest);
		
		RequestDAO requestDAO = new RequestDAO();
		requestDAO.deleteRequest(requestBean.getBuyer(), item);
		
	}
	
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
		acceptedOrder.addParameter("status", "accepted");
		acceptedOrder.addParameter("item", itemID.toString());
		
		order.setSellerStatus(true);
		
		if(Boolean.TRUE.equals(order.isAccepted())){
			order.setStartDate(new Date());
		}
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getBuyer().getUsername(), acceptedOrder);

		orderDAO.updateOder(order);
		
		
	}
	
	
	public void rejectOrder(OrderBean orderBean) {
		Notification rejectedOrder = new Notification();
		
		Integer orderID = orderBean.getOrderID();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		String seller = order.getInvolvedItem().getSeller().getUsername();
		Integer itemID = order.getInvolvedItem().getItemInSaleID();
		
		rejectedOrder.setSender(seller);
		rejectedOrder.setDate(new Date());
		rejectedOrder.setType(NotificationType.ORDER);
		rejectedOrder.addParameter("status", "rejected");
		rejectedOrder.addParameter("item", itemID.toString());
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getBuyer().getUsername(), rejectedOrder);
		
		orderDAO.deleteOrder(orderID);
	}
	
	public OrderBean generateOrderSummary(Integer orderID) {
		OrderBean orderSummary = new OrderBean();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		User buyer = order.getBuyer();
		ItemInSale item = order.getInvolvedItem();
		UserBean buyerData = new UserBean();
		ItemInSaleBean itemData = new ItemInSaleBean();
		
		buyerData.setUserID(buyer.getUsername());
		itemData.setItemID(item.getItemInSaleID());
		itemData.setItemName(item.getReferredItem().getName());
		itemData.setPrice(item.getPrice());
		//TODO gestire media
		
		orderSummary.setBuyer(buyerData);
		orderSummary.setInvolvedItem(itemData);
		return orderSummary;
	}
	
	public Boolean verifyPaymentCode(OrderBean orderBean){
		String code = orderBean.getCode();
		OrderDAO orderDAO = new OrderDAO();
		Integer itemID = orderBean.getOrderID();
		Order order = orderDAO.selectOrder(itemID);
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
		ItemInSale item = itemDAO.selectItemInSale(itemID);
		
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
			sender.sendNotification(order.getBuyer().getUsername(), enteredCode);
			return true;
		}
		else
			return false;
	}
	

	
}
