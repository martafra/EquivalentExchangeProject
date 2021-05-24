package logic.controller.graphic;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import logic.bean.ItemBean;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.ItemAdController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class PostAnAdView extends SceneManageable{

	private ArrayList<ItemBean> items = new ArrayList<>();
	private ItemAdController controller = new ItemAdController();
	
	private UserBean user = new UserBean();
	
	
	@FXML
	private ComboBox itemList;
	@FXML
	private Button publish;
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		LoginBean loggedUser = (LoginBean) bundle.getBean("loggedUser");
		user.setName(loggedUser.getUserID());
		
		items = (ArrayList<ItemBean>) controller.getItemsList();
		
		for(ItemBean item : items) {
			itemList.getItems().add(item.getItemName());
		}
		
	}
	
	public void publishAd() {
		controller.post(items, null)
	}
	
}
