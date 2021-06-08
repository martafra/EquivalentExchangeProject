package logic.controller.graphic;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import logic.bean.ItemAdBean;
import logic.bean.ItemBean;
import logic.bean.ItemInSaleBean;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.ItemDetailsController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ItemDetailsView extends SceneManageable {
	
	private LoginBean loggedUser;
	private UserBean seller;
	private ItemBean item;
	private ItemAdBean itemAd;
	
	private ItemDetailsController controller = new ItemDetailsController();
	
	@FXML
    private VBox vbox;
	@FXML
    private Label sellerText;
	@FXML
    private Label titleText; 
	@FXML
    private Label priceText;
	@FXML
    private Label descText;
	@FXML
    private Label typeText;
	@FXML
    private Label genreText;
	@FXML
    private Label addressText;
	@FXML
    private Label conditionText;
	@FXML
    private Label label1;
	@FXML
    private Button buyBtn;
	@FXML
    private ImageView imgV;
    
    @Override
    public void onLoad(Bundle bundle) {
    	
    	super.onLoad(bundle);
    	
    	loggedUser = (LoginBean)bundle.getBean("loggedUser");
    	ItemInSaleBean itemInSale = (ItemInSaleBean)bundle.getBean("selectedItem");
    	
    	itemAd =  controller.getItemAdByID(itemInSale.getItemID());
    	seller = controller.getUserByID(itemAd.getSellerID());
    	item = controller.getItemByID(itemAd.getReferredItemID());
    	
    	
    	titleText.setText(item.getItemName());
    	descText.setText(itemAd.getDescription());
    	descText.setWrapText(true);
    	genreText.setText(item.getGenre());
    	conditionText.setText(itemAd.getCondition());
    	addressText.setText(itemAd.getAddress());
    	addressText.setWrapText(true);
    	
    	sellerText.setText(seller.getName());
    	priceText.setText(itemInSale.getPrice().toString());
    	imgV.setImage(new Image(itemInSale.getMediaPath()));
    	
    	String itemDetails;
    	if (item.getType() =='B') {
    		itemDetails = String.format("Author: %s %nEdition: %s %nNumber Of Pages: %s %nPublishing House: %s",
        			item.getAuthor(), 
        			item.getEdtion().toString(),
        			item.getNumberOfPages().toString(),
        			item.getPublishingHouse());
    		typeText.setText("BOOK");
    	}
    	else if(item.getType() =='M') {
    		itemDetails = String.format("Duration: %s", item.getDuration());	
    		typeText.setText("MOVIE");
    	}
    	else {
    		itemDetails = "SWITCH, PLAY, XBOX"; //TODO prendere dalla bean
    		typeText.setText("VIDEOGAME");
    	}
    	label1.setText(itemDetails);
    
    }
    
    public void clickOnBuy() {
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
    	String buyerID = loggedUser.getUserID();
    	Integer itemInSaleID = itemAd.getItemInSaleID();
    	controller.clickOnBuy(buyerID, itemInSaleID); 	
    }
    
    
}
