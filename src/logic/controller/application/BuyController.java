package logic.controller.application;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import logic.DAO.ItemDAO;
import logic.DAO.ItemInSaleDAO;
import logic.DAO.OrderDAO;
import logic.DAO.UserDAO;
import logic.bean.OrderBean;
import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.User;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.Notification;

public class BuyController {
	
	//arriva la notifica che il seller ha accettato
	public Boolean orderNotification(Integer orderID) {
		
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		if (order.isAccepted()) {	//so già che il seller ha accettato, quindi verifivo il buyer
			if (!orderAccepted(orderID)){ 
				//se orderAccepted non è andato a buon fine:
				order.setBuyerStatus(false);
				order.setStartDate(null);
				orderDAO.updateOder(order);
			}	
			
			return true;
		}
		else {						// buyer non ha ancora accettato
			return false;
		}
		
	}
	
	public Boolean orderAccepted(Integer orderID) { //metodo che viene chiamato quando entrambi hanno accettato
		
		UserDAO userD = new UserDAO();
		OrderDAO orderD = new OrderDAO();
		Order order = orderD.selectOrder(orderID);
		
		User buyer = order.getBuyer();
		ItemInSale involvedItem = order.getInvolvedItem();
	
		if (buyer.decreaseCredit(involvedItem.getPrice())) {
			//se lo scalo del credito è andato a buon fine
			userD.updateUser(buyer);
			
			String code = generateCode();
			
			order.setCode(code);
			//order.setStartDate(new Date());
			//startTimer(order.getOrderID(), 259200000L - (new Date().getTime() - order.getStartDate().getTime()));
			startTimer(order.getOrderID(), 10000L);
			orderD.updateOder(order);
			
			//TODO ORDER SUMMARY
			return true;
		}
		
		return false;
	}
	
	public void acceptOrder(OrderBean orderBean) {
		Integer orderID = orderBean.getOrderID();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		String buyer = order.getBuyer().getUsername();
		Integer itemID = order.getInvolvedItem().getItemInSaleID();
		
		Notification acceptedOrder = new Notification();
		acceptedOrder.setSender(buyer);
		acceptedOrder.setDate(new Date());
		acceptedOrder.setType(NotificationType.ORDER);
		acceptedOrder.addParameter("status", "accepted");
		acceptedOrder.addParameter("item", itemID.toString());
		
		order.setBuyerStatus(true);
		orderDAO.updateOder(order);
		
		if(order.isAccepted()){
			order.setStartDate(new Date());
			orderDAO.updateOder(order);
		//Se entrambi hanno già accettato:	
			if (!orderAccepted(order.getOrderID())){ 
				//se orderAccepted non è andato a buon fine:
				order.setBuyerStatus(false);
				order.setStartDate(null);
				orderDAO.updateOder(order);
				return;
			}	
			//TODO se orderAccepted è andato a buon fine:
		}
		
		//Tranne nel caso in cui orderAccepted non è andato a buon fine -> invio la notifica al seller che il buyer ha accettato
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getInvolvedItem().getSeller().getUsername(), acceptedOrder);
		
	
	}
	
	public void rejectOrder(OrderBean orderBean) {
		Integer orderID = orderBean.getOrderID();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		String buyer = order.getBuyer().getSurname();
		Integer itemID = order.getInvolvedItem().getItemInSaleID();
		
		Notification rejectedOrder = new Notification();
		rejectedOrder.setSender(buyer);
		rejectedOrder.setDate(new Date());
		rejectedOrder.setType(NotificationType.ORDER);
		rejectedOrder.addParameter("status", "rejected");
		rejectedOrder.addParameter("item", itemID.toString());
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getBuyer().getUsername(), rejectedOrder);
		
		orderDAO.deleteOrder(orderID);
	}
	
	
	public String generateCode() {
		String lower = "abcdefghijklmnopqrstuvwxyz";
        String upper = lower.toUpperCase();
        String numeri = "0123456789";
        String perRandom = upper + lower + numeri;
        int lunghezzaRandom = 20;

        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder(lunghezzaRandom);
        for (int i = 0; i < lunghezzaRandom; i++) {
            int randomInt = sr.nextInt(perRandom.length());
            char randomChar = perRandom.charAt(randomInt);
            sb.append(randomChar);
        }
        return sb.toString();
	}
	
	public void startTimer(Integer orderId, Long delay) {
		
		Timer timer = new Timer("Order" + orderId);
		TimerTask task = new TimerTask() {
			public void run() {//azione svolta quando il timer scade
				Platform.runLater(() -> {
					OrderDAO orderD = new OrderDAO();
					Order order = orderD.selectOrder(orderId);
					order.setBuyerStatus(false);
					order.setSellerStatus(false);
					order.setStartDate(null);
					order.setOrderDate(null);
					order.setCode(null);
					orderD.updateOder(order);
					//TODO ripristinare credito
					//orderD.deleteOrder(orderId);
				});
			}
		};
		
		timer.schedule(task, delay);
		
	}
	

}
