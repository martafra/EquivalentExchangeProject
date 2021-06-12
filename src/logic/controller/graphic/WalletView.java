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
			Pane orderCase = fillOrderCase(order);
			orderBox.getChildren().add(orderCase);
		}
		
		
		
	}
	
	private Pane fillOrderCase(OrderBean orderBean) {
		Pane orderCase = null;
		ObservableList<Node> attr = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/OrderCase.fxml"));
		try {
			orderCase = (Pane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String item = orderBean.getInvolvedItem().getItemName();
		String buyer = (orderBean.getBuyer()).getUserID();
		String seller = ((orderBean.getInvolvedItem()).getSeller()).getUserID();
		Image pic = new Image(orderBean.getInvolvedItem().getMediaPath());
		Integer orderID = orderBean.getOrderID();
		Integer price = orderBean.getInvolvedItem().getPrice();
		Date startDate = orderBean.getStartDate();
		
		attr = orderCase.getChildren();
		for (Node n: attr) {
			switch(n.getId()) {
				case "orderIDLabel":
					((Label)n).setText("Order ID #" + orderID);
					break;
				case "orderPic":
					((ImageView)n).setImage(pic);
					break;
				case "orderItemLabel":
					((Label)n).setText("\""+item+"\"");
					n.setOnMouseClicked(new EventHandler<MouseEvent>() {
				        @Override
				        public void handle(MouseEvent event) {
				        	Bundle bundle = getBundle();
				        	bundle.addBean("selectedItem", orderBean.getInvolvedItem());
				            goToScene("itemDetails");
				        }
				    });
					break;
				case "startDateLabel":
					if(startDate != null)
						((Label)n).setText("placed on " + startDate.toString());
					else
						((Label)n).setText("placed on:");
					break;
				case "orderUserLabel":
					UserBean userBean = new UserBean();
					String userLabel;
					if (!(buyer.equals(loggedUser.getUserID()))) {
						System.out.println("prova");
						userLabel = "Buyer: " + buyer;
						userBean.setUserID(buyer);
					}
					else {
						userLabel = "Seller: " + seller;
						userBean.setUserID(seller);
					}
					((Label)n).setText(userLabel);
					n.setOnMouseClicked(new EventHandler<MouseEvent>() {
				        @Override
				        public void handle(MouseEvent event) {
				        	Bundle bundle = getBundle();
				        	bundle.addBean("otherUser", userBean);
				            goToScene("userprofile");
				        }
				    });
					break;
				case "orderAmount":
					((Label)n).setText(price.toString() + " coin");
					
			}
		}
		return orderCase;
		
	}
	
	
}
