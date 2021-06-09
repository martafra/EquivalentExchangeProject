package logic.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.DAO.OrderDAO;
import logic.DAO.UserDAO;
import logic.DAO.UserProfileDAO;
import logic.bean.ChatBean;
import logic.bean.UserBean;
import logic.entity.ChatMessage;
import logic.entity.Order;
import logic.entity.User;
import logic.entity.UserProfile;
import logic.enumeration.NotificationType;
import logic.support.connection.MessageSender;
import logic.support.other.MailBox;

public class ChatController{
	
	public void sendMessage(ChatBean messageData){
		
		String senderID = messageData.getSender();
		String text = messageData.getMessageText();
		Date messageDate = new Date();
		String receiverID = messageData.getReceiver();
	
		ChatMessage message = new ChatMessage(senderID, messageDate, text);
		
		MessageSender sender = new MessageSender();
		sender.sendChatMessage(receiverID, message);
		
    }
	
	public List<UserBean> getActiveChats(UserBean user){
		ArrayList<UserBean> activeUsers = new ArrayList<>();
		
		UserDAO userDAO = new UserDAO();
		OrderDAO orderDAO = new OrderDAO();
		UserProfileDAO userProfileDAO = new UserProfileDAO();
		
		List<Order> userOrders = orderDAO.selectAllOrders(user.getUserID());
		
		for(Order order : userOrders) {
			User otherUser;
			if(user.getUserID().equals(order.getBuyer().getUsername())) {
				otherUser = order.getInvolvedItem().getSeller();
			}else {
				otherUser = order.getBuyer();
			}
			
			UserProfile profileData = userProfileDAO.selectProfileByUsername(otherUser.getUsername(), true);
			UserBean otherUserBean = new UserBean();
			otherUserBean.setName(otherUser.getName());
			otherUserBean.setLastName(otherUser.getSurname());
			otherUserBean.setUserID(otherUser.getUsername());
			otherUserBean.setEmail(otherUser.getEmail());
			otherUserBean.setProfilePicPath(profileData.getProfilePicturePath());
			activeUsers.add(otherUserBean);
		}
	
		return activeUsers;
	}
	
	public List<ChatBean> getMessagesByUser(MailBox box, UserBean sender){
		
		ArrayList<ChatMessage> messages = (ArrayList<ChatMessage>) box.getMessages();
		ArrayList<ChatBean> filteredMessages = new ArrayList<>();
		for(ChatMessage message : messages) {
			if(message.getSender().equals(sender.getUserID())) {
				ChatBean messageBean = new ChatBean();
				messageBean.setSender(message.getSender());
				messageBean.setMessageText(message.getText());
				filteredMessages.add(messageBean);
			}
		}
		return filteredMessages;
		
	}
	
	
	public ChatBean getLastMessageSent(MailBox box) {
		ArrayList<ChatMessage> messages = (ArrayList<ChatMessage>) box.getMessages();
		ChatMessage message = messages.get(messages.size()-1);
		ChatBean messageBean = new ChatBean();
		messageBean.setMessageText(message.getText());
		messageBean.setSender(message.getSender());
		return messageBean;
	}
	
	public Boolean getChatNotifications(MailBox box) {
		return !box.getNotifications(NotificationType.CHAT).isEmpty();
	}
	
}