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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
	private GridPane summaryCodePane;
	@FXML
	private Button summaryVerify;
	@FXML 
	private TextField codeField;
	@FXML
	private StackPane codeStackPane;
	@FXML
	private TextField codeLabel;
	@FXML
	private Label codeErrorLabel;
	
	private SellController sController = new SellController();
	private BuyController bController = new BuyController();
	private ItemDetailsController iController = new ItemDetailsController();
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private OrderBean order;
	private ItemDetailsBean item;
	private HBox inputCodeBox = null;
	private HBox outputCodeBox = null;
	AnimationTimer timer;
	
	
	@FXML
	public void verifySummaryCode() {
		String code = codeField.getText();
		order.setCode(code);
		if(Boolean.TRUE.equals(sController.verifyPaymentCode(order))) {
			goToScene("wallet");
		}else {
			codeErrorLabel.setText("Wrong code! Contact the buyer if the error persists!");
		}
	}
	
	
	@Override
	public void onLoad(Bundle bundle){
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		codeErrorLabel.setText("");
		summaryStart.setText("");
		summaryEnd.setText("");
		
		if(codeStackPane.getChildren().size() == 2) {
			inputCodeBox = (HBox) codeStackPane.getChildren().get(1);
			outputCodeBox = (HBox) codeStackPane.getChildren().get(0);
		}
		codeStackPane.getChildren().clear();
		
		OrderBean selectedOrder = (OrderBean) bundle.getBean("selectedOrder");
		order = sController.generateOrderSummary(selectedOrder.getOrderID());
		item = iController.getItemDetails(order.getInvolvedItem().getItemID());
		
		requiredOperations();
		
		String seller = item.getSeller().getUserID();
		
		switch(getOrderStatus(loggedUser, order)) {
			case 0:
				summaryCodeLabel.setText("");
				break;
			case 1:
				summaryCodeLabel.setText("Enter payment code");
				codeStackPane.getChildren().add(inputCodeBox);
				break;
			case 2:
				summaryCodeLabel.setText("Payment code");
				codeStackPane.getChildren().add(outputCodeBox);
				codeLabel.setText(order.getCode());
				break;
			case 3: 
				summaryCodeLabel.setText("Payment code");
				codeStackPane.getChildren().add(outputCodeBox);
				codeLabel.setText(order.getCode());
				break;
			case 4:
				break;
			default:
				break;
		}
		
		
		if(seller.equals(loggedUser.getUserID())){
			summaryUser.setText("Buyer: " + order.getBuyer().getUserID());
		}
		else{
			summaryUser.setText("Seller: " + seller);
		}
		
		timer = null;
		if(order.getStartDate() != null && order.getOrderDate() == null){
			Integer remainingTime = bController.checkRemainingTime(order);
			timer = new CountdownTimer(remainingTime, summaryEnd);
			timer.start();	
		}
	}
	
	@Override
	public void onExit() {
		super.onExit();
		if(timer != null) {
			timer.stop();
		}
	}
	
	private Integer getOrderStatus(UserBean loggedUser, OrderBean order) {
		
		if(order.getStartDate() == null && order.getOrderDate() == null) {
			//Order not started yet
			return 0;	
		}
		if(order.getStartDate() != null && order.getOrderDate() == null) {
			
			//Order started but not finished
			if(loggedUser.getUserID().equals(item.getSeller().getUserID())) {
				//Seller View
				return 1;
			}else {
				//Buyer View
				return 2;
			}
		}
		if(order.getStartDate() != null && order.getOrderDate() != null) {
			//Order finished
			return 3;
		}
		
		return 4;
		
	}
	
	
	public void requiredOperations() {
		
		summaryPic.setImage(new Image(item.getMediaPath()));
		summaryName.setText(item.getItemName());
		summaryCondition.setText(item.getCondition());
		summaryPrice.setText(item.getPrice().toString());
		
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
			summaryStart.setText("Order started on: " + format.format(order.getStartDate()));
		
		if( order.getOrderDate() != null )
			summaryEnd.setText("Order finished on: " + format.format(order.getOrderDate()));
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
			stop();
			bController.checkRemainingTime(order);
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