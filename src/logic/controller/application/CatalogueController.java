package logic.controller.application;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import logic.DAO.ItemInSaleDAO;
import logic.bean.ItemInSaleBean;
import logic.entity.ItemInSale;
import logic.enumeration.BookGenre;
import logic.enumeration.Condition;
import logic.enumeration.MovieGenre;
import logic.enumeration.VideoGameGenre;

public class CatalogueController {
	
	public List<ItemInSaleBean> getListItemInSaleBean() {
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		List<ItemInSale> itemInSaleList = itemInSaleDAO.getItemsInSaleList();
		
		return createItemInSaleBeanList(itemInSaleList);
	}
	
	public List<ItemInSaleBean> getListItemInSaleBeanFiltered(String[] filters) {
		ItemInSaleDAO itemInSaleDAO = new ItemInSaleDAO();
		List<ItemInSale> itemInSaleList = itemInSaleDAO.getItemsInSaleListFiltered(filters);
		
		return createItemInSaleBeanList(itemInSaleList);
	}
	
	
	public List<ItemInSaleBean> createItemInSaleBeanList(List<ItemInSale> itemInSaleList) {
		List<ItemInSaleBean> itemInSaleBeanList =  new ArrayList<>();
		
			for (ItemInSale item : itemInSaleList) {
			
			ItemInSaleBean itemInSaleBean = new ItemInSaleBean();
			
			itemInSaleBean.setItemID(item.getItemInSaleID());
			itemInSaleBean.setItemName(item.getReferredItem().getName());
			itemInSaleBean.setPrice(item.getPrice());
			itemInSaleBean.setMediaPath(item.getMedia().get(0));
			itemInSaleBeanList.add(itemInSaleBean);
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
	
	

}
