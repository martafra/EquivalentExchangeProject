package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.DAO.ItemInSaleDAO;
import logic.DAO.UserDAO;
import logic.DAO.WishlistDAO;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.entity.ItemInSale;
import logic.entity.User;


public class WishlistController {
	ItemDetailsController controller = new ItemDetailsController();
	
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
			itemInSaleBean.setSeller(controller.getUserData(item.getSeller().getUsername()));
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
		Boolean ret=false;
		if (wishlist.checkItemInWishlist(user, itemInSale)) {
			ret= true;
		}
		return ret;
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
