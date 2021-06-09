package logic.controller.graphic;

import java.io.IOException;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.bean.ItemInSaleBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.controller.application.SellController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class SellerPanelView extends SceneManageable{
	
	@FXML
	private ScrollPane itemPanel;
	@FXML
	private FlowPane itemBox;
	@FXML
	private VBox requestBox;
	
	private SellController sellController = new SellController();
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		List<ItemInSaleBean> itemBeans = sellController.getItemList(loggedUser);
		itemBox.getChildren().clear();
		for(ItemInSaleBean itemBean : itemBeans) {
			Pane productCase = fillProductCase(itemBean);
			itemBox.getChildren().add(productCase);
		}
		

		List<RequestBean> requestBeans = sellController.getRequestList(loggedUser);
		requestBox.getChildren().clear();
		for(RequestBean requestBean: requestBeans) {
			Pane requestCase = fillRequestCase(requestBean);
			requestBox.getChildren().add(requestCase);
		}
		

	}
	
	private Pane fillProductCase(ItemInSaleBean itemBean) {
		Pane productCase = null;
		ObservableList<Node> attr = null;
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
		System.out.println("percorso: " + itemBean.getMediaPath());
		
		attr = productCase.getChildren();
		for(Node n: attr) {
			switch (n.getId()) {
				case "productPreview":
					((ImageView) n).setImage(pic);
					break;
				case "productName":
					((Label) n).setText(name);
					n.setOnMouseClicked(new EventHandler<MouseEvent>() {
				        @Override
				        public void handle(MouseEvent event) {
				        	Bundle bundle = getBundle();
				        	bundle.addBean("selectedItem", itemBean);
				            goToScene("itemDetails");
				        }
				    });
					break;
				case "productPrice":
					((Label) n).setText(price.toString());
			}
			
		}
		return productCase;
	}
	
	private Pane fillRequestCase(RequestBean requestBean) {
		Pane requestCase = null;
		ObservableList<Node> attr = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/RequestCase.fxml"));
		
		try {
			requestCase = (Pane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Image pic = new Image(requestBean.getReferredItemBean().getMediaPath());
		String item = requestBean.getReferredItemBean().getItemName();
		String buyer = requestBean.getBuyer();
		String note = requestBean.getNote();
		
		final Pane finalCase = requestCase;
		
		attr = requestCase.getChildren();
		for(Node n: attr) {
			switch(n.getId()) {
				case "requestPic":
					((ImageView) n).setImage(pic);
					break;
				case "requestItem":
					((Label) n).setText("Request referred to "+ item);
					n.setOnMouseClicked(new EventHandler<MouseEvent>() {
				        @Override
				        public void handle(MouseEvent event) {
				        	Bundle bundle = getBundle();
				        	bundle.addBean("selectedItem", requestBean.getReferredItemBean());
				            goToScene("itemDetails");
				        }
				    });
					break;
				case "requestBuyer":
					((Label) n).setText("From user "+ buyer);
					n.setOnMouseClicked(new EventHandler<MouseEvent>() {
				        @Override
				        public void handle(MouseEvent event) {
				        	Bundle bundle = getBundle();
				        	UserBean userBean = new UserBean();
				        	userBean.setUserID(buyer);
				        	bundle.addBean("selectedUser", userBean);
				            goToScene("userprofile");
				        }
				    });
					break;
				case "requestNote":
					((Label) n).setText("\"" + note + "\"");
					break;
				case "requestAcc":
					((Button) n).setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							sellController.acceptRequest(requestBean);
							requestBox.getChildren().remove(finalCase);
						}
					});
					break;
				case "requestRej":
					((Button) n).setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							sellController.rejectRequest(requestBean);
							requestBox.getChildren().remove(finalCase);
						}
					});
					break;
					
					
			}
		}
		
		return requestCase;
	}
	
}
