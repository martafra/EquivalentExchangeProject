package logic.controller.graphic;

import java.io.IOException;

import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.bean.OrderBean;
import logic.bean.UserBean;
import logic.controller.application.WalletController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class WalletView extends SceneManageable{
	
	 @FXML
	 private VBox orderBox;
	 @FXML
	 private Label creditLabel;
	 
	 private WalletController walletController = new WalletController();
	 UserBean loggedUser = null;
	 
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		loggedUser = (UserBean)bundle.getBean("loggedUser");
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		creditLabel.setText(walletController.getCredit(loggedUser).toString());
		
		List<OrderBean> orders = walletController.getOrderList(loggedUser);
		orderBox.getChildren().clear();
		for(OrderBean order: orders) {
			OrderCase orderCase = new OrderCase(order, loggedUser);
			
			orderCase.getUserLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedUser", orderCase.getUserData());
		            goToScene("userprofile");
		        }
		    });
			
			orderCase.getItemLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedItem", order.getInvolvedItem());
		            goToScene("itemDetails");
		        }
		    });	
			orderCase.getOrderLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedOrder", order);
		            goToScene("ordersummary");
		        }
		    });	
			orderBox.getChildren().add(orderCase.getBody());
		}	
	}
}
