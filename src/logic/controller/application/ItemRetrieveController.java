package logic.controller.application;

import java.util.ArrayList;
import java.util.List;

import logic.DAO.ItemDAO;
import logic.bean.ItemBean;
import logic.entity.Item;

public class ItemRetrieveController {
	public List<ItemBean> getItemsList(){
		ItemDAO itemDAO = new ItemDAO(); 
		ArrayList<Item> items = (ArrayList<Item>) itemDAO.getItemsList();
		
		ArrayList<ItemBean> itemData = new ArrayList<>();
		
		for(Item item : items) {
				ItemBean itemBean = new ItemBean(item.getItemID(), item.getName());
				itemBean.setType(item.getType());
				itemData.add(itemBean);
		}
		return itemData;
	}
}
