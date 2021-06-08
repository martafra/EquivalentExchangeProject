package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import logic.bean.ItemInSaleBean;


public class ItemCatalogueView {
	@FXML
    private VBox vbox;
	@FXML
    private Label titleText; 
	@FXML
    private Label priceText;
	@FXML
    private ImageView imageView;
    
    
    public void setView(ItemInSaleBean item) {
    	titleText.setText(item.getItemName());
    	priceText.setText(item.getPrice().toString());
    	imageView.setImage(new Image(item.getMediaPath()));
   		
    }

}
