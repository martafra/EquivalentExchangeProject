package logic.controller.graphic;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import logic.bean.ItemInSaleBean;
import logic.bean.OrderReviewBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.controller.application.ProfileController;
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
	@FXML
	private ScrollPane reviewScrollPane;
	@FXML 
	private VBox reviewsBox;
	
	private SellController sellController = new SellController();
	private ProfileController profileController = new ProfileController();
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		List<ItemInSaleBean> itemBeans = sellController.getItemList(loggedUser);
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
		
		List<OrderReviewBean> reviewBeans = profileController.getReviewList(loggedUser);
		for (OrderReviewBean review: reviewBeans) {
			ReviewCase reviewCase = new ReviewCase(review);
			System.out.println(review.getBuyerNote());
			reviewsBox.getChildren().add(reviewCase.getBody());
		}
	}
	
	@Override
    public void onExit() {
    	super.onExit();
		reviewsBox.getChildren().clear();
		itemBox.getChildren().clear();
		requestBox.getChildren().clear();
    }
}
