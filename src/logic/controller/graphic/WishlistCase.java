package logic.controller.graphic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.bean.ItemInSaleBean;

public class WishlistCase extends GraphicWidget{
	private Pane boxBody;
	
	public WishlistCase(ItemInSaleBean itemData){
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/WishlistCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		Label priceLabel = (Label) getComponent("priceLabel");
		priceLabel.setText( itemData.getPrice() + " Coins");
		
		ImageView itemPic = (ImageView) getComponent("itemPic");
		itemPic.setImage(new Image(itemData.getMediaPath()));
		
		Label sellerLabel = (Label) getComponent("sellerLabel");
		sellerLabel.setText("Seller: " + itemData.getSeller().getUserID());
		
		Label itemLabel = (Label) getComponent("itemLabel");
		itemLabel.setText(itemData.getItemName());
		
		Button removeBtn = (Button) getComponent("removeBtn");
		
		Text availabilityLabel = (Text) getComponent("availabilityLabel");
		if (itemData.getAvailability()) {
			availabilityLabel.setText("Available");
		}
		else{
			availabilityLabel.setText("Unavailable");
			availabilityLabel.setFill(Color.RED);
			removeBtn.setTextFill(Color.RED);
		}
		
	}
	
	public Pane getBody() {
		return this.boxBody;
	}
	
	public Label getItemLabel() {
		return (Label) this.getComponent("itemLabel");
	}
	
	public Button getRemoveBtn() {
		return (Button) this.getComponent("removeBtn");
	}
	
	


}
