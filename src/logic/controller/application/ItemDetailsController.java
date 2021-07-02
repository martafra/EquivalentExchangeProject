package logic.controller.application;



import logic.support.connection.MessageSender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import logic.DAO.ItemDAO;
import logic.DAO.ItemInSaleDAO;
import logic.DAO.RequestDAO;
import logic.DAO.UserDAO;
import logic.DAO.WishlistDAO;
import logic.DAO.UserProfileDAO;
import logic.bean.ItemBean;
import logic.bean.ItemDetailsBean;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.entity.Book;
import logic.entity.Item;
import logic.entity.ItemInSale;
import logic.entity.Movie;
import logic.entity.Request;
import logic.entity.User;
import logic.entity.UserProfile;
import logic.entity.Videogame;
import logic.enumeration.NotificationType;
import logic.support.other.Notification;

public class ItemDetailsController {
	
	public UserBean getUserData(String username) {	
		var bean = new UserBean();
		UserDAO userDAO = new UserDAO();
		UserProfileDAO profileDAO = new UserProfileDAO();
		User seller = userDAO.selectUser(username);
		
		UserProfile sellerProfile = profileDAO.selectProfileByUsername(seller.getUsername(), true);
		
		bean.setEmail(seller.getEmail());
		bean.setName(seller.getName());
		bean.setLastName(seller.getSurname());
		bean.setProfilePicPath(sellerProfile.getProfilePicturePath());
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
		bean.setMedia((ArrayList<String>)itemInSale.getMedia()); 
		//cast a 'ArrayList<String>' poiche' itemInSale.getMedia() ritorna un List<String>, da modificare?
		
		return bean;
	}
	
	public List<ItemInSaleBean> getOtherItem(String seller, String itemInSale) {
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		List<ItemInSale> itemInSaleList = itemInSaleDAO.getOtherItem(seller, itemInSale);
		
		List<ItemInSaleBean> itemInSaleBeanList =  new ArrayList<>();
		int i = 0;
		for (ItemInSale item : itemInSaleList) {
			i++;
			if (i == 4) {
				break;
			}
			
			ItemInSaleBean itemInSaleBean = new ItemInSaleBean();
			itemInSaleBean.setItemID(item.getItemInSaleID());
			itemInSaleBean.setItemName(item.getReferredItem().getName());
			itemInSaleBean.setPrice(item.getPrice());
			itemInSaleBean.setMediaPath(item.getMedia().get(0));
			itemInSaleBean.setSeller(getUserData(item.getSeller().getUsername()));
			itemInSaleBeanList.add(itemInSaleBean);
			
		}
		return itemInSaleBeanList;
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
			if(book.getGenre() != null)
				bean.setGenre(book.getGenre().toString());	//TODO mettere controllo null se non è obbligatorio inserirlo
			bean.setPublishingHouse(book.getPublishingHouse());
		}
		else if (itemType == 'M') {
			Movie movie = (Movie)item;
			bean.setDuration(movie.getDuration()); 
			if(movie.getGenre() != null)
				bean.setGenre(movie.getGenre().toString()); //TODO mettere controllo null se non è obbligatorio inserirlo
		}
		else {
			Videogame videogame = (Videogame)item;
			if(videogame.getGenre() != null)
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
	
	public boolean checkRequest(String buyer, Integer item) {
		RequestDAO requestDAO = new RequestDAO();
		Request request = requestDAO.selectRequest(buyer, item);
		if (request == null) {
			return false;
		}
		return true;
	}
	
	
	
	
}
