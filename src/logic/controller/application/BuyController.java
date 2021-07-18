package logic.controller.application;



import java.security.SecureRandom;

import java.util.Date;
import java.util.List;
import java.util.Map;

import logic.bean.OrderBean;
import logic.bean.OrderReviewBean;
import logic.dao.ItemInSaleDAO;
import logic.dao.OrderDAO;
import logic.dao.UserDAO;
import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.OrderReview;
import logic.entity.User;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.exception.InsufficientCreditException;
import logic.support.interfaces.SaleController;
import logic.support.other.MailBox;
import logic.support.other.Notification;

public class BuyController implements SaleController{
	
	public void updateReview(OrderReviewBean reviewBean) {
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(reviewBean.getOrderID());
		OrderReview review = new OrderReview();
		review.setSellerReliability(reviewBean.getSellerReliability());
		review.setSellerAvailability(reviewBean.getSellerAvailability());
		review.setItemCondition(reviewBean.getItemCondition());
		review.setBuyerNote(reviewBean.getBuyerNote());
		order.setOrderReview(review);
		orderDAO.updateOder(order);
	}
	
	public Boolean orderAccepted(Integer orderID) throws InsufficientCreditException { //metodo che viene chiamato quando entrambi hanno accettato
		
		UserDAO userD = new UserDAO();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		User buyer = order.getBuyer();
		ItemInSale involvedItem = order.getInvolvedItem();
		if (buyer.decreaseCredit(involvedItem.getPrice())) {
			
				userD.updateUser(buyer);
				order.setBuyerStatus(true);
				order.setCode(generateCode());
			
				if(Boolean.TRUE.equals(order.isAccepted())){ //se entrambi hanno accettato -> il buyer e' il secondo ad aver cliccato accetta
				//entrambi hanno già accettato:	
					order.setStartDate(new Date());
				}
				orderDAO.updateOder(order);
				return true;
		}
	
		return false;
	}
	
	@Override
	public void acceptOrder(OrderBean orderBean) throws InsufficientCreditException { //buyer clicca su accetta ordine

		
		Integer orderID = orderBean.getOrderID();
		OrderDAO orderDAO = new OrderDAO();
		Order order = orderDAO.selectOrder(orderID);
		
		String buyer = order.getBuyer().getUsername();
		Integer itemID = order.getInvolvedItem().getItemInSaleID();
		
		
		if (Boolean.FALSE.equals(orderAccepted(order.getOrderID()))){ 
			//se orderAccepted non è andato a buon fine:
			//non notifico nulla al venditore, per lui e' come se il buyer non avesse ancora mai cliccato su accetta
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
		
		String buyer = order.getBuyer().getUsername();
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
		ItemInSale item = order.getInvolvedItem();
		Integer itemID = item.getItemInSaleID();
		
		Notification rejectedOrder = new Notification();
		rejectedOrder.setSender(buyer);
		rejectedOrder.setDate(new Date());
		rejectedOrder.setType(NotificationType.ORDER);
		rejectedOrder.addParameter("status", "rejected");
		rejectedOrder.addParameter("item", itemID.toString());
		
		item.setAvailability(true);
		itemDAO.updateItemInSale(item);
		
		MessageSender sender = new MessageSender();
		sender.sendNotification(order.getInvolvedItem().getSeller().getUsername(), rejectedOrder);
		
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
		
		if (order.getOrderDate() != null) {
			return -2;
		}
		if(order.getStartDate() == null) {
			return -1;
		}
		Integer remainingTime;
		Long elapsedTime;
		Date now = new Date();
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
	
	public boolean checkNotification(MailBox mailbox, Integer orderID) {
		List<Notification> notification = mailbox.getNotifications(NotificationType.ORDER);
		for ( Notification i : notification) {
			Map<String, String> parameters = i.getParameters();
			if (parameters.containsKey("orderID")) {
				Integer order = Integer.parseInt(parameters.get("orderID"));
				if (orderID.equals(order) && parameters.get("code").equals("valid")) {
					return true;
				}
			}	
		}
		return false;
	}
	

}
