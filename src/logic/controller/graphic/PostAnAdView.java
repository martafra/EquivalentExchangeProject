package logic.controller.graphic;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import logic.bean.ItemBean;
import logic.controller.application.ItemAdController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class PostAnAdView extends SceneManageable{

	private ArrayList<ItemBean> items = new ArrayList<>();
	private ItemAdController controller = new ItemAdController(); 
	
	@FXML
	private ComboBox itemList;
	
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		items = (ArrayList<ItemBean>) controller.getItemsList();
		
		for(ItemBean item : items) {
			itemList.getItems().add(item.getItemName());
		}
		
	}
	
}
