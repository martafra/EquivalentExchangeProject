package logic.controller.graphic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.bean.OrderBean;
import logic.bean.UserBean;

public class OrderCase extends GraphicWidget{

	private Pane boxBody;
	private UserBean userData = null;
	
	public OrderCase(OrderBean orderData, UserBean loggedUser){
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/OrderCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		Label orderIDLabel = (Label) getComponent("orderIDLabel");
		orderIDLabel.setText("Order ID #" + orderData.getOrderID());
		
		ImageView orderPic = (ImageView) getComponent("orderPic");
		orderPic.setImage(new Image(orderData.getInvolvedItem().getMediaPath()));
		
		Label orderItemLabel = (Label) getComponent("orderItemLabel");
		orderItemLabel.setText("\""+ orderData.getInvolvedItem().getItemName() +"\"");
		
		Label dateLabel = (Label) getComponent("dateLabel");
		
		if(orderData.getOrderDate() != null) {
			dateLabel.setText("Order finished on: " + orderData.getOrderDate());
		}
		else if(orderData.getStartDate() != null) {
			dateLabel.setText("Order started on: " + orderData.getStartDate());
		}
		else {
			dateLabel.setText("In Progress");
		}
		
		Label orderUserLabel = (Label) getComponent("orderUserLabel");
		
		if(!(orderData.getBuyer().getUserID().equals(loggedUser.getUserID()))) {
			orderUserLabel.setText("Buyer: " + orderData.getBuyer().getUserID());
			userData = orderData.getBuyer();
		}
		else {
			orderUserLabel.setText("Seller: " + loggedUser.getUserID());
			userData = loggedUser;
		}
		
		Label orderAmount = (Label) getComponent("orderAmount");
		orderAmount.setText(orderData.getInvolvedItem().getPrice().toString() + " coins");
	}
	
	public Pane getBody() {
		return this.boxBody;
	}
	
	public UserBean getUserData() {
		return this.userData;
	}
	
	public Label getUserLabel() {
		return (Label) this.getComponent("orderUserLabel");
	}
	
	public Label getOrderLabel() {
		return (Label) this.getComponent("orderIDLabel");
	}
	
	public Label getItemLabel() {
		return (Label) this.getComponent("orderItemLabel");
	}
	
}
