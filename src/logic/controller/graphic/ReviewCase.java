package logic.controller.graphic;



import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import logic.bean.OrderReviewBean;

public class ReviewCase extends GraphicWidget{
	
	private Pane boxBody;
	
	
	public ReviewCase(OrderReviewBean review){
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/ReviewCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		RatingView relS = new RatingView(5);
		relS.setEditable(false);
		RatingView avaS = new RatingView(5);
		avaS.setEditable(false);
		RatingView conS = new RatingView(5);
		conS.setEditable(false);
		relS.setPaneWidth(100f);
		avaS.setPaneWidth(100f);
		conS.setPaneWidth(100f);
		
		HBox sellerReliability = (HBox) getComponent("sellerReliability");
		HBox sellerAvailability = (HBox) getComponent("sellerAvailability");
		HBox itemCondition = (HBox) getComponent("itemCondition");
		Label reviewID = (Label) getComponent("reviewID");
		
		relS.setValue(review.getSellerReliability());
		avaS.setValue(review.getSellerAvailability());
		conS.setValue(review.getItemCondition());
		
		sellerReliability.getChildren().add(relS);
		sellerAvailability.getChildren().add(avaS);
		itemCondition.getChildren().add(conS);
		reviewID.setText("Order ID #" + review.getOrderID());
	}
	
	public Pane getBody() {
		return this.boxBody;
	}
}
