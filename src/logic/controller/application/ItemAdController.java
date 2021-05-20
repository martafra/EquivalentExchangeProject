package logic.controller.application;

import java.util.ArrayList;

import logic.DAO.ItemDAO;
import logic.DAO.ItemInSaleDAO;
import logic.DAO.UserDAO;
import logic.bean.ItemAdBean;
import logic.bean.UserBean;
import logic.entity.Item;
import logic.entity.ItemInSale;
import logic.entity.User;
import logic.enumeration.Condition;

public class ItemAdController {
	
	public boolean post(ItemAdBean itemBean, UserBean userBean) {
		
		ItemInSaleDAO itemDAO = new ItemInSaleDAO();
				
		Integer price = itemBean.getPrice();
		String description = itemBean.getDescription();
		Condition condition = itemBean.getCondition();
		ArrayList<String> media = itemBean.getMedia();
		String address = itemBean.getAddress();

		User seller = new UserDAO().selectUser(userBean.getUserID());
		Item referredItem = new ItemDAO().selectItem(itemBean.getReferredItemID());
		
		ItemInSale item = new ItemInSale(price, description, condition, referredItem, seller);

		for(String mediaPath : media){
			item.addMedia(mediaPath);
		}

		itemDAO.insertItemInSale(item);

		return true;
	}
}
