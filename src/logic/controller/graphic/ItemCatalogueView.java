package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import logic.bean.ItemInSaleBean;
import logic.support.other.Bundle;
import logic.support.other.PaneManager;
import logic.support.other.SceneManageable;


public class ItemCatalogueView extends SceneManageable {
	
	private ItemInSaleBean itemInSale;
	private CatalogueView catalogueView;
	
	
	@FXML
    private VBox vbox;
	@FXML
    private Label titleText; 
	@FXML
    private Label priceText;
	@FXML
    private ImageView imageView;
	
	
    
    
    public void setView(CatalogueView catalogueView, ItemInSaleBean item) {
    	this.catalogueView = catalogueView;
    	itemInSale = item;
    	titleText.setText(item.getItemName());
    	priceText.setText(item.getPrice().toString());
    	imageView.setImage(new Image(item.getMediaPath()));
   		
    }
    
    public void click() {
    	catalogueView.goToDetails(itemInSale);
    }
    
    
    

}
