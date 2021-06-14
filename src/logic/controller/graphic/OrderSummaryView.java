package logic.controller.graphic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
	
	private SellController sController = new SellController();
	private ItemDetailsController iController = new ItemDetailsController();
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@Override
	public void onLoad(Bundle bundle){
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		String logged = loggedUser.getUserID();
		OrderBean order = (OrderBean) bundle.getBean("selectedOrder");
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
			summaryCode.add(codeField, 0, 1);
			summaryVerify.setVisible(true);
			summaryVerify.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					sController.verifyPaymentCode(order);
				}
			});
		}
		else{
			summaryUser.setText("Seller: " + seller);
			summaryCodeLabel.setText("Payment code");
			Label codeField = new Label();
			codeField.setText(order.getCode());
			summaryCode.add(codeField, 0, 1);
		}
		if(order.getSellerStatus()) {
			summarySeller.setText("Order accepted");
		}
		else {
			summarySeller.setText("Order not accepted yet");
		}
		if(order.getBuyerStatus()) {
			summaryBuyer.setText("Order accepted");
		}
		else {
			summaryBuyer.setText("Order not accepted yet");
		}
		summaryStart.setText(format.format(order.getStartDate()));
		summaryStart.setText(format.format(order.getOrderDate()));
		
		
	}
}
