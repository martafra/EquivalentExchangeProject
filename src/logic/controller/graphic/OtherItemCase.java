package logic.controller.graphic;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;

public class OtherItemCase  extends GraphicWidget{
	private Pane boxBody;
	private ItemInSaleBean itemInSaleData = null;
	
	public OtherItemCase(ItemInSaleBean itemData){
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/OtherItemCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		Label priceOther = (Label) getComponent("priceOther");
		priceOther.setText( itemData.getPrice() + " Coins");
		
		ImageView imgOther = (ImageView) getComponent("imgOther");
		imgOther.setImage(new Image(itemData.getMediaPath()));
		
		Label sellerOther = (Label) getComponent("sellerOther");
		sellerOther.setText(itemData.getSeller().getUserID());
		
	}
	
	public Pane getBody() {
		return this.boxBody;
	}
	
	public ItemInSaleBean getItemInSaleData() {
		return this.itemInSaleData;
	}
	
	public ImageView getImg() {
		return (ImageView) this.getComponent("imgOther");
	}
	
	public Label getSellerLabel() {
		return (Label) this.getComponent("sellerOther");
	}
	
	

}
