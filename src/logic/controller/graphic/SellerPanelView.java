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
			ProductCase productCase = new ProductCase(itemBean);
			productCase.getProductName().setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedItem", itemBean);
		            goToScene("itemDetails");
		        }
		    });
			itemBox.getChildren().add(productCase.getBody());
		}
		

		List<RequestBean> requestBeans = sellController.getRequestList(loggedUser);
		requestBox.getChildren().clear();
		for(RequestBean requestBean: requestBeans) {
			RequestCase requestCase = new RequestCase(requestBean);
			
			requestCase.getItemLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedItem", requestBean.getReferredItemBean());
		            goToScene("itemDetails");
		        }
		    });
			requestCase.getBuyerLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	UserBean userBean = new UserBean();
		        	userBean.setUserID(requestBean.getBuyer());
		        	bundle.addBean("selectedUser", userBean);
		            goToScene("userprofile");
		        }
		    });
			requestCase.getAcceptButton().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					sellController.acceptRequest(requestBean);
					requestBox.getChildren().remove(requestCase.getBody());
				}
			});
			requestCase.getRejectButton().setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					sellController.rejectRequest(requestBean);
					requestBox.getChildren().remove(requestCase.getBody());
				}
			});
			
			requestBox.getChildren().add(requestCase.getBody());
		}
	}
}
