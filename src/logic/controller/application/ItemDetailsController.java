package logic.controller.application;


import logic.support.connection.MessageSender;

import java.util.ArrayList;
import java.util.Date;
import logic.DAO.ItemDAO;
import logic.DAO.ItemInSaleDAO;
import logic.DAO.RequestDAO;
import logic.DAO.UserDAO;
import logic.bean.ItemBean;
import logic.bean.ItemDetailsBean;
import logic.bean.UserBean;
import logic.entity.Book;
import logic.entity.Item;
import logic.entity.ItemInSale;
import logic.entity.Movie;
import logic.entity.Request;
import logic.entity.User;
import logic.entity.Videogame;
import logic.enumeration.NotificationType;
import logic.support.other.Notification;

public class ItemDetailsController {
	
	public UserBean getUserData(String username) {	
		var bean = new UserBean();
		UserDAO userDAO = new UserDAO();
		
		User seller = userDAO.selectUser(username);
		
		bean.setEmail(seller.getEmail());
		bean.setName(seller.getName());
		bean.setLastName(seller.getSurname());
		
		bean.setUserID(seller.getUsername());
		
		return bean;
	}
	
	public ItemDetailsBean getItemDetails(Integer itemInSaleID) {
		var bean = new ItemDetailsBean();
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		
		ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(itemInSaleID);

		bean.setItemName(itemInSale.getReferredItem().getName());
		bean.setItemInSaleID(itemInSale.getItemInSaleID());
		bean.setReferredItemID(itemInSale.getReferredItem().getItemID());
		bean.setSeller(getUserData(itemInSale.getSeller().getUsername()));
		bean.setPrice(itemInSale.getPrice());
		bean.setAddress(itemInSale.getAddress());
		bean.setCondition(itemInSale.getCondition().toString());
		bean.setDescription(itemInSale.getDescription());
		bean.setMedia((ArrayList<String>)itemInSale.getMedia()); //cast a 'ArrayList<String>' poiche' itemInSale.getMedia() ritorna un List<String>, da modificare?
		
		return bean;
	}
	
	public ItemBean getItemByID(Integer itemID) {
		var bean = new ItemBean();
		ItemDAO itemDAO = new ItemDAO();
		
		Item item = itemDAO.selectItem(itemID);
		bean.setItemName(item.getName());
		bean.setPublishingDate(item.getPublishingDate());
		char itemType = item.getType();
		bean.setType(itemType);
		
		if (itemType == 'B') {
			Book book = (Book)item;
			bean.setAuthor(book.getAuthor()); 
			bean.setEdition(book.getEdtion()); 
			bean.setNumberOfPages(book.getNumberOfPages());
			bean.setGenre(book.getGenre().toString());	//TODO mettere controllo null se non è obbligatorio inserirlo
			bean.setPublishingHouse(book.getPublishingHouse());
		}
		else if (itemType == 'M') {
			Movie movie = (Movie)item;
			bean.setDuration(movie.getDuration()); 
			bean.setGenre(movie.getGenre().toString()); //TODO mettere controllo null se non è obbligatorio inserirlo
		}
		else {
			Videogame videogame = (Videogame)item;
			bean.setGenre(videogame.getGenre().toString()); //TODO mettere controllo null se non è obbligatorio inserirlo
		}	
		return bean;
	}

	public void clickOnBuy(String buyerID, Integer itemInSaleID, String requestMsg) {
		
		UserDAO userDAO = new UserDAO();
		User buyer = userDAO.selectUser(buyerID);
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(itemInSaleID);
		
		Request request = new Request(buyer, itemInSale, false, requestMsg); 
		RequestDAO requestD = new RequestDAO();
		requestD.insertRequest(request);
		
		Notification notification = new Notification(); 
		notification.setSender(buyerID);
		notification.setDate(new Date());
		notification.setType(NotificationType.REQUEST);
		notification.addParameter("itemInSaleID", itemInSale.getItemInSaleID().toString());
		
		MessageSender msgSender = new MessageSender();
		msgSender.sendNotification(itemInSale.getSeller().getUsername(), notification);
		
		
	}
}
