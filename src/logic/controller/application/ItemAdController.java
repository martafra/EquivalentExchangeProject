package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.DAO.ItemDAO;
import logic.DAO.ItemInSaleDAO;
import logic.DAO.UserDAO;
import logic.bean.ItemAdBean;
import logic.bean.ItemBean;
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
		Condition condition = Condition.valueOfLabel(itemBean.getCondition());
		ArrayList<String> media = itemBean.getMedia();
		String address = itemBean.getAddress();

		User seller = new UserDAO().selectUser(userBean.getUserID());
		Item referredItem = new ItemDAO().selectItem(itemBean.getReferredItemID());
		
		ItemInSale item = new ItemInSale(price, description, condition, referredItem, seller);

		
		if(!media.isEmpty())
			for(String mediaPath : media)
				item.addMedia(mediaPath);
		else
			item.setMedia(media);

		itemDAO.insertItemInSale(item);

		return true;
	}
	
	public List<ItemBean> getItemsList(){
		ItemDAO itemDAO = new ItemDAO(); 
		ArrayList<Item> items = (ArrayList<Item>) itemDAO.getItemsList();
		
		ArrayList<ItemBean> itemData = new ArrayList<>();
		
		for(Item item : items) {
			itemData.add(new ItemBean(item.getItemID(), item.getName()));
		}
		return itemData;
	}
	
	public List<String> getConditionTypes(){
		
		ArrayList<String> conditions = new ArrayList<>();
		for(Condition condition : Condition.values()) {
			conditions.add(condition.toString());
		}
		return conditions;
	}
	
}
