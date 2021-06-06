package logic.controller.graphic;

import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.controller.application.SellController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class SellerPanelView extends SceneManageable{
	
	@FXML
	private ScrollPane itemPanel;
	@FXML
	private VBox itemBox;
	
	private SellController sellController = new SellController();
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		itemBox.setPrefHeight(5000);
		List<ItemInSaleBean> itemBeans = sellController.getItemList(loggedUser);
		for(ItemInSaleBean itemBean : itemBeans) {
			Pane productCase = fillCase(itemBean);
			itemBox.getChildren().add(productCase);
		}
		

	}
	
	private Pane fillCase(ItemInSaleBean itemBean) {
		Pane productCase = null;
		ObservableList<Node> prova = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/ProductCase.fxml"));
		try {
			productCase = (Pane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer price = itemBean.getPrice();
		String name = itemBean.getItemName();
		Image pic = new Image(itemBean.getMediaPath());
		
		prova = productCase.getChildren();
		for(Node n: prova) {
			switch (n.getId()) {
				case "productPreview":
					((ImageView) n).setImage(pic);
					break;
				case "productName":
					((Label) n).setText(name);
					n.setOnMouseClicked(new EventHandler<MouseEvent>() {
				        @Override
				        public void handle(MouseEvent event) {
				            System.out.println("mouse click detected! "+event.getSource());
				        }
				    });
					break;
				case "productPrice":
					((Label) n).setText(price.toString());
			}
			
		}
		return productCase;
	}
	
}
