package logic.controller.graphic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import logic.bean.ItemDetailsBean;
import logic.bean.OrderBean;
import logic.bean.UserBean;
import logic.controller.application.BuyController;
import logic.controller.application.ItemDetailsController;
import logic.controller.application.SellController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class OrderSummaryView extends SceneManageable{
	
	@FXML
	private AnchorPane orderSummary;
	@FXML
	private ImageView summaryPic;
	@FXML
	private Label summaryName;
	@FXML
	private Label summaryCondition;
	@FXML
	private Label summaryPrice;
	@FXML
	private Label summaryUser;
	@FXML
	private Label summarySeller;
	@FXML
	private Label summaryBuyer;
	@FXML
	private Label summaryStart;
	@FXML
	private Label summaryEnd;
	@FXML
	private Label summaryCodeLabel;
	@FXML
	private GridPane summaryCode;
	@FXML
	private Button summaryVerify;
	@FXML
	private Label summaryTimer;
	
	private SellController sController = new SellController();
	private BuyController bController = new BuyController();
	private ItemDetailsController iController = new ItemDetailsController();
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private OrderBean order;
	
	@Override
	public void onLoad(Bundle bundle){
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}

		

		
		String logged = loggedUser.getUserID();
		OrderBean selectedOrder = (OrderBean) bundle.getBean("selectedOrder");
		order = sController.generateOrderSummary(selectedOrder.getOrderID());
		ItemDetailsBean item = iController.getItemDetails(order.getInvolvedItem().getItemID());
		
		summaryPic.setImage(new Image(item.getMediaPath()));
		summaryName.setText(item.getItemName());
		summaryCondition.setText(item.getCondition());
		summaryPrice.setText(item.getPrice().toString());
		String seller = item.getSeller().getUserID();
		if(seller.equals(logged)){
			summaryUser.setText("Buyer: " + order.getBuyer().getUserID());
			summaryCodeLabel.setText("Enter payment code");
			TextField codeField = new TextField();
			
			if(order.getOrderDate() != null) {
				Label codeLabel = new Label(order.getCode());
				summaryCode.add(codeLabel, 0, 1);
				summaryCodeLabel.setText("Payment Code:");
			}else {
				summaryCode.add(codeField, 0, 1);
			}
				
			summaryVerify.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					order.setCode(codeField.getText());
					sController.verifyPaymentCode(order);
				}
			});
		}
		else{
			summaryUser.setText("Seller: " + seller);
			summaryCodeLabel.setText("Payment code");
			Label codeField = new Label();
			codeField.setText(order.getCode());
			summaryVerify.setVisible(false);
			summaryCode.add(codeField, 0, 1);
		}
		if(order.getSellerStatus()) {
			summarySeller.setText("Order accepted by seller");
		}
		else {
			summarySeller.setText("Order not accepted by seller");
		}
		if(order.getBuyerStatus()) {
			summaryBuyer.setText("Order accepted by buyer");
		}
		else {
			summaryBuyer.setText("Order not accepted by buyer");
		}
		
		if( order.getStartDate() != null)
			summaryStart.setText("Order started on: "+ format.format(order.getStartDate()));
		
		if( order.getOrderDate() != null )
			summaryStart.setText(format.format("Order finished on: " + order.getOrderDate()));

		if(order.getStartDate() != null && order.getOrderDate() == null){
			Integer remainingTime = bController.checkRemainingTime(order);
			AnimationTimer timer = new CountdownTimer(remainingTime, summaryTimer);
			timer.start();

			
		}
	}

	private class CountdownTimer extends AnimationTimer{

    private long previousTime = 0;
	private Double accumulator = 0d;
	private Integer countdownValue = 0;
	private Label countdownLabel;
	

	public CountdownTimer(Integer value, Label countdownLabel){

		if(value > 0){
			countdownValue = value;
		}else{
			countdownValue = 0;
		}
		this.countdownLabel = countdownLabel;
	}


	@Override
	public void handle(long time) {
        long deltaTime = time - previousTime;
        previousTime = time;

		accumulator += deltaTime * (Double) Math.pow(10, -9);

		if(accumulator >= 1){
			accumulator = 0d;
			countdownValue--;

			String composedTime = convertToTime(countdownValue);
			countdownLabel.setText(composedTime);
		}

		if(countdownValue <= 0){
			bController.checkRemainingTime(order);
			stop();
		}

		//System.out.println((Double) (deltaTime * (Double) Math.pow(10, -9)));
	}

	private String convertToTime(Integer seconds){
		
		if(seconds <= 0) 
			return "00:00:00";

        Integer h = seconds / 3600;
        Integer m = seconds % 3600 / 60;
        Integer s = seconds % 60; // Less than 60 is the second, enough 60 is the minute
                 
		return String.format("%02d:%02d:%02d", h, m, s);

	}


}

}
