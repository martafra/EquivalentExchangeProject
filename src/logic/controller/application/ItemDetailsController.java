package logic.controller.application;

import logic.support.connection.MessageSender;

import java.util.Date;

import logic.DAO.ItemDAO;
import logic.DAO.ItemInSaleDAO;
import logic.DAO.RequestDAO;
import logic.DAO.UserDAO;
import logic.bean.ItemAdBean;
import logic.bean.ItemBean;
import logic.bean.LoginBean;
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
	
	public UserBean getUserByID(String username) {	
		var bean = new UserBean();
		UserDAO userDAO = new UserDAO();
		
		User seller = userDAO.selectUser(username);
		
		bean.setEmail(seller.getEmail());
		bean.setName(seller.getName());
		bean.setLastName(seller.getSurname());
		
		bean.setUserID(seller.getUsername());
		
		return bean;
	}
	
	public ItemAdBean getItemAdByID(Integer itemInSaleID) {
		var bean = new ItemAdBean();
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		
		ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(itemInSaleID);
		
		bean.setItemInSaleID(itemInSale.getItemInSaleID());
		bean.setReferredItemID(itemInSale.getReferredItem().getItemID());
		bean.setSellerID(itemInSale.getSeller().getUsername());
		bean.setPrice(itemInSale.getPrice());
		bean.setAddress(itemInSale.getAddress());
		bean.setCondition(itemInSale.getCondition().toString());
		bean.setDescription(itemInSale.getDescription());
		//bean.setMedia(itemInSale.getMedia());
		
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

	public void clickOnBuy(String buyerID, Integer itemInSaleID) {
		
		UserDAO userDAO = new UserDAO();
		User buyer = userDAO.selectUser(buyerID);
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(itemInSaleID);
		
		Request request = new Request(buyer, itemInSale, false, "Ciao, vorrei comprare questo oggetto"); //TODO far inserire il testo all'utente
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
