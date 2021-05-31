package logic.controller.application;

import java.util.Date;

import logic.DAO.ItemInSaleDAO;
import logic.DAO.OrderDAO;
import logic.DAO.RequestDAO;
import logic.DAO.UserDAO;
import logic.bean.OrderBean;
import logic.bean.RequestBean;
import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.User;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.Notification;

public class SellController {
	
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
		requestDAO.deleteRequest(seller, item);
		
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
		requestDAO.deleteRequest(seller, item);
		
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
		
		if(order.isAccepted()){
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

	
}
