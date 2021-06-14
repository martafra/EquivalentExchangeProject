package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import logic.bean.ItemInSaleBean;
import logic.bean.OrderBean;
import logic.bean.UserBean;
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
	
	private SellController controller = new SellController();
	
	@Override
	public void onLoad(Bundle bundle){
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		String logged = loggedUser.getUserID();
		OrderBean order = (OrderBean) bundle.getBean("selectedOrder");
		
		summaryPic.setImage(new Image(order.getInvolvedItem().getMediaPath()));
		summaryName.setText(order.getInvolvedItem().getItemName());
		summaryPrice.setText(order.getInvolvedItem().getPrice().toString());
		String seller = order.getInvolvedItem().getSeller().getUserID();
		if(seller.equals(logged)){
			summaryUser.setText("Buyer: " + order.getBuyer());
		}
		else{
			summaryUser.setText("Seller: " + seller);
		}
		
		
	}
}
