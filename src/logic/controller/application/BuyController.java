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
import logic.support.interfaces.SaleController;
import logic.support.other.Notification;

public class BuyController implements SaleController{
	
	//arriva la notifica che il seller ha accettato
	public Boolean orderNotification(Integer orderID) {
		
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		if (order.isAccepted()) {	//so già che il seller ha accettato, quindi verifivo il buyer
			//Il buyer e' il secondo ad accettare
			
			if (!orderAccepted(orderID)){ 
				//orderAccepted non è andato a buon fine, visto che il seller e' il secondo ad aver cliccato accetta gli invio la notifica
				
				String buyer = order.getBuyer().getUsername();
			
				Notification errorOrder = new Notification();
				errorOrder.setSender(buyer);
				errorOrder.setDate(new Date());
				errorOrder.setType(NotificationType.ORDER);
				errorOrder.addParameter("status", "insufficient credit");
				errorOrder.addParameter("order", orderID.toString());
				
				MessageSender sender = new MessageSender();
				sender.sendNotification(order.getInvolvedItem().getSeller().getUsername(), errorOrder);
			}	
			
			return true;
		}
		else {						// buyer non ha ancora accettato
			return false;
		}
		
	}
	
	private Boolean orderAccepted(Integer orderID) { //metodo che viene chiamato quando entrambi hanno accettato
		
		UserDAO userD = new UserDAO();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		User buyer = order.getBuyer();
		ItemInSale involvedItem = order.getInvolvedItem();
		System.out.println("Sono dentro orderAccepted");
		System.out.println("Credito attuale: " + buyer.getWallet().getCurrentCredit());
		if (buyer.decreaseCredit(involvedItem.getPrice())) {
			//se lo scalo del credito e' andato a buon fine
			System.out.println("Il credito è stato scalato");
			System.out.println("Nuovo credito: " + buyer.getWallet().getCurrentCredit());
			
			userD.updateUser(buyer);
			order.setBuyerStatus(true);
			order.setCode(generateCode());
			
			if(order.isAccepted()){ //se entrambi hanno accettato -> il buyer e' il secondo ad aver cliccato accetta
				//entrambi hanno già accettato:	
				
				System.out.println("Hanno entrambi accettato");
				order.setStartDate(new Date());
			}
			orderDAO.updateOder(order);
			return true;
		}
		
		//order.setBuyerStatus(false);
		//order.setStartDate(null);
		//orderDAO.updateOder(order);
		return false;
	}
	
	@Override
	public void acceptOrder(OrderBean orderBean) { //buyer clicca su accetta ordine
		
		System.out.println("Sono in accetta ordine");
		
		Integer orderID = orderBean.getOrderID();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		String buyer = order.getBuyer().getUsername();
		Integer itemID = order.getInvolvedItem().getItemInSaleID();
		
		
		if (!orderAccepted(order.getOrderID())){ 
			//se orderAccepted non è andato a buon fine:
			//non notifico nulla al venditore, per lui e' come se il buyer non avesse ancora mia cliccato su accetta
			return;
		}
		
		Notification acceptedOrder = new Notification();
		acceptedOrder.setSender(buyer);
		acceptedOrder.setDate(new Date());
		acceptedOrder.setType(NotificationType.ORDER);
		acceptedOrder.addParameter("status", "accepted");
		acceptedOrder.addParameter("item", itemID.toString());
		
		//Tranne nel caso in cui orderAccepted non è andato a buon fine -> invio la notifica al seller che il buyer ha accettato
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getInvolvedItem().getSeller().getUsername(), acceptedOrder);
		
	
	}
	
	@Override
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
	
	
	private String generateCode() {
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
	
	public Integer checkRemainingTime(OrderBean orderData) {
		
		OrderDAO orderD = new OrderDAO();			
		final Integer orderTime = 60*2;
		Order order = orderD.selectOrder(orderData.getOrderID());
		
		if(order.getStartDate() == null) {
			return -1;
		}
		Integer remainingTime;
		Long elapsedTime;
		Date now = new Date();
		System.out.println("buy controller " + now);
		Date startDate = order.getStartDate();

		elapsedTime = now.getTime() - startDate.getTime();
		elapsedTime = elapsedTime / 1000;
		remainingTime = orderTime - Integer.valueOf(elapsedTime.toString());

		if(remainingTime <= 0){
			order.setBuyerStatus(false);
			order.setSellerStatus(false);
			order.setStartDate(null);
			order.setOrderDate(null);
			order.setCode(null);
			orderD.updateOder(order);
			restoreCredit(order);
			
		}

		return Math.max(0, remainingTime);
	}
	
	public void restoreCredit(Order order) {
		
		User user = order.getBuyer();
		Integer price = order.getInvolvedItem().getPrice();
		user.increaseCredit(price);
		
		UserDAO userDAO = new UserDAO();
		userDAO.updateUser(user);
		
		
	}
	

}
