package logic.controller.graphic;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import logic.bean.ItemInSaleBean;
import logic.support.other.Bundle;

public class ProductCase extends GraphicWidget{
	
	private Pane caseBody;
	
	public ProductCase(ItemInSaleBean itemBean) {
		
		try {
			caseBody = new FXMLLoader(getClass().getResource("/logic/view/ProductCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadComponents(caseBody);
		
		ImageView productPreview = (ImageView) getComponent("productPreview");
		productPreview.setImage(new Image(itemBean.getMediaPath()));
		
		Label productName = (Label) getComponent("productName");
		productName.setText(itemBean.getItemName());
		
		Label productPrice = (Label) getComponent("productPrice");
		productPrice.setText(itemBean.getPrice() + " coins");
		
	}
	
	public Pane getBody() {
		return this.caseBody;
	}
	
	public Label getProductName() {
		return (Label) getComponent("productName");
	}
	
}
