package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.DAO.ItemInSaleDAO;
import logic.DAO.UserDAO;
import logic.DAO.UserProfileDAO;
import logic.DAO.WishlistDAO;
import logic.bean.ItemDetailsBean;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.entity.ItemInSale;
import logic.entity.Order;
import logic.entity.User;
import logic.entity.UserProfile;

public class WishlistController {
	
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
	
	public List<ItemInSaleBean> getItemInWishlist(UserBean userBean) {
		String username = userBean.getUserID();
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		List<ItemInSale> wishlist = itemInSaleDAO.getItemInSaleWishlist(username);
		
		List<ItemInSaleBean> itemInSaleBeanList =  new ArrayList<>();
		for (ItemInSale item: wishlist) {
			ItemInSaleBean itemInSaleBean = new ItemInSaleBean();
			
			itemInSaleBean.setItemID(item.getItemInSaleID());
			itemInSaleBean.setItemName(item.getReferredItem().getName());
			itemInSaleBean.setPrice(item.getPrice());
			itemInSaleBean.setMediaPath(item.getMedia().get(0));
			itemInSaleBean.setSeller(getUserData(item.getSeller().getUsername()));
			itemInSaleBean.setAvailability(item.getAvailability());
			
			itemInSaleBeanList.add(itemInSaleBean);
		}
		
		return itemInSaleBeanList;
	}
	
	
	public void addToWishlist(String userID, Integer itemID) {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.selectUser(userID);
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(itemID);
		
		WishlistDAO wishlist = new WishlistDAO();
		wishlist.insertToWishlist(user, itemInSale);
		
	}
	
	public boolean checkWishlist(String userID, Integer itemID) {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.selectUser(userID);
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(itemID);
		
		WishlistDAO wishlist = new WishlistDAO();
		if (wishlist.checkItemInWishlist(user, itemInSale)) {
			return true;
		}
		else{
			return false;
		
		}
	}
	
	public void removeFromWishlist(String userID, Integer itemID) {
		UserDAO userDAO = new UserDAO();
		User user = userDAO.selectUser(userID);
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		ItemInSale itemInSale = itemInSaleDAO.selectItemInSale(itemID);
		
		WishlistDAO wishlist = new WishlistDAO();
		wishlist.deleteFromWishlist(user, itemInSale);
		
	}

}
