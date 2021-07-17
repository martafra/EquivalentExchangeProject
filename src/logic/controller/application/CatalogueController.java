package logic.controller.application;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logic.bean.ItemInSaleBean;
import logic.dao.ItemInSaleDAO;
import logic.entity.ItemInSale;
import logic.enumeration.BookGenre;
import logic.enumeration.MovieGenre;
import logic.enumeration.VGConsole;
import logic.enumeration.VideoGameGenre;

public class CatalogueController {
	
	public List<ItemInSaleBean> getListItemInSaleBeanFiltered(String loggedUser, Map<String, String> filters) {
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		List<ItemInSale> itemInSaleList = itemInSaleDAO.getItemsInSaleListFiltered(loggedUser, filters);
		
		return createItemInSaleBeanList(itemInSaleList);
	}
	
	
	public List<ItemInSaleBean> createItemInSaleBeanList(List<ItemInSale> itemInSaleList) {
		List<ItemInSaleBean> itemInSaleBeanList =  new ArrayList<>();
		
			for (ItemInSale item : itemInSaleList) {
			if(item.getAvailability()) {
				ItemInSaleBean itemInSaleBean = new ItemInSaleBean();
			
				itemInSaleBean.setItemID(item.getItemInSaleID());
				itemInSaleBean.setItemName(item.getReferredItem().getName());
				itemInSaleBean.setPrice(item.getPrice());
				itemInSaleBean.setMediaPath(item.getMedia().get(0));
				itemInSaleBeanList.add(itemInSaleBean);
			}
		}
		return itemInSaleBeanList;
	}
	
	public List<String> getGenre(char type){	
		ArrayList<String> genres = new ArrayList<>();
		if (type == 'B') {
			for(BookGenre genre : BookGenre.values()) {
				genres.add(genre.toString());
				
			}
			return genres;
		}
		else if(type == 'M') {
			for(MovieGenre genre : MovieGenre.values()) {
				genres.add(genre.toString());
			}
			return genres;
		}
		else {
			for(VideoGameGenre genre : VideoGameGenre.values()) {
				genres.add(genre.toString());
			}
			return genres;
		}
	}
	
	public List<String> getConsole(){	
		ArrayList<String> consoles = new ArrayList<>();
		for(VGConsole console : VGConsole.values()) {
				consoles.add(console.toString());
			}
			return consoles;
	}
	
	
}
