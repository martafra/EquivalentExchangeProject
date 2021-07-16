package logic.controller.graphic;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import logic.bean.ItemInSaleBean;
import logic.bean.OrderReviewBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.bean.UserProfileBean;
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
	@FXML
	private VBox averageRatingBox;
	@FXML
	private VBox chartBox;
	
	@FXML
	private void goToPostAnAd(){
		goToScene("postad");
	}
	
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
			productCase.getComponent("removeProduct").setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	sellController.removeProduct(itemBean);
		        	Bundle bundle = getBundle();
		        	bundle.addBean("loggedUser", loggedUser);
		            goToScene("sellerpanel");
		        }
		    });
			productCase.onSellerPanel();
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
			
			Label note = new Label();
			note.setPrefWidth(250);
			note.setWrapText(true);
			HBox hReviewBox = new HBox();
			hReviewBox.setPrefWidth(486);
			hReviewBox.setPrefHeight(100);
			ReviewCase reviewCase = new ReviewCase(review);
			((Label)reviewCase.getComponent("reviewID")).setText("Order ID #" + review.getOrderID());
			note.setText("Note from buyer: " + "\"" + review.getBuyerNote() + "\"");
			hReviewBox.getChildren().add(reviewCase.getBody());
			hReviewBox.getChildren().add(note);
			reviewsBox.getChildren().add(hReviewBox);
		}
		
		UserProfileBean loggedProfileBean = profileController.getUserProfileData(loggedUser);
		OrderReviewBean averageReview = new OrderReviewBean();
		averageReview.setSellerReliability(loggedProfileBean.getOverallReliabiltyValue());
		averageReview.setSellerAvailability(loggedProfileBean.getOverallAvailabilityValue());
		averageReview.setItemCondition(loggedProfileBean.getOverallConditionsValue());
		ReviewCase averageCase = new ReviewCase(averageReview);
		((Label)averageCase.getComponent("reviewID")).setText("");
		averageRatingBox.getChildren().add(averageCase.getBody());

		
		CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Parameters");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Values");

        BarChart<String, Number> averageChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("Average rating");

        dataSeries1.getData().add(new Data<>("Reliability", loggedProfileBean.getOverallReliabiltyValue()));
        dataSeries1.getData().add(new Data<>("Availability"  , loggedProfileBean.getOverallAvailabilityValue()));
        dataSeries1.getData().add(new Data<>("Item Condition"  , loggedProfileBean.getOverallConditionsValue()));

        averageChart.getData().add(dataSeries1);
        averageChart.applyCss();
        
        chartBox.getChildren().add(averageChart);
		
	}
	
	@Override
    public void onExit() {
    	super.onExit();
		reviewsBox.getChildren().clear();
		itemBox.getChildren().clear();
		requestBox.getChildren().clear();
		averageRatingBox.getChildren().clear();
		chartBox.getChildren().clear();
    }
}
