package logic.controller.graphic;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import logic.bean.ItemBean;
import logic.bean.ItemDetailsBean;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.bean.UserProfileBean;
import logic.controller.application.ItemDetailsController;
import logic.controller.application.ProfileController;
import logic.controller.application.WishlistController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ItemDetailsView extends SceneManageable {
	
	private UserBean loggedUser;
	private UserBean seller;
	private ItemBean item;
	private ItemDetailsBean itemDetails;
	private Integer maxCharacter = 300;
	private Stage secondaryStage;
	private RatingView userR = new RatingView(5);
	private ItemDetailsController controller = new ItemDetailsController();
	private ProfileController controllerProfile = new ProfileController();
	private WishlistController wishlistController = new WishlistController();
	
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
	@FXML
	private Label back;
	@FXML
	private TextArea requestArea;
	@FXML 
	private Button send;
	@FXML
	private Label character;
	@FXML
	private Circle imgSeller;
	@FXML
	private VBox imgItem;
	@FXML
	private Label msgLabel;
	@FXML
	private Label sellerOther;
	@FXML
	private Label priceOther;
	@FXML
	private HBox otherItemBox;
	@FXML 
	private Text wishlist;
	@FXML
	private ScrollPane descScrollPane;
	@FXML
	private HBox sellerReviews;
	@FXML
	private Label sellerDetails;
	@FXML
	private Label errorLabel;
	
	
	
    
    @Override
    public void onLoad(Bundle bundle) {
    	
    	super.onLoad(bundle);
    	
    	loggedUser = (UserBean)bundle.getBean("loggedUser");
    	ItemInSaleBean itemInSale = (ItemInSaleBean)bundle.getBean("selectedItem");
    	itemDetails =  controller.getItemDetails(itemInSale.getItemID());

    	seller = itemDetails.getSeller();
    	item = controller.getItemByID(itemDetails.getReferredItemID());
    	
    	
    	titleText.setText(item.getItemName());
    	imgV.setImage(new Image(itemInSale.getMediaPath()));
    	fillImg();
    	descText.setText(itemDetails.getDescription());
    	descText.setWrapText(true);
    	
    	genreText.setText(item.getGenre());
    	conditionText.setText(itemDetails.getCondition());
    	addressText.setText(itemDetails.getAddress());
    	addressText.setWrapText(true);
    	String itemDetailsStr;
    	String language = "";
    	if (item.getLanguage() != null) {
			language = item.getLanguage().toLowerCase();
		}
    	if (item.getType() =='B') {
    		itemDetailsStr = String.format("Author: %s %nEdition: %s %nNumber Of Pages: %s %nPublishing House: %s %nLanguage: %s",
        			item.getAuthor(), 
        			item.getEdtion().toString(),
        			item.getNumberOfPages().toString(),
        			item.getPublishingHouse(),
    				language);
    		typeText.setText("BOOK");
    	}
    	else if(item.getType() =='M') {
    		itemDetailsStr = String.format("Duration: %s %nLanguage: %s", item.getDuration()+" min", language);	
    		typeText.setText("MOVIE");
    	}
    	else {
    		String console = "";
    		if (item.getConsole() != null) {
    			 console = item.getConsole().toLowerCase();
    		}
    		itemDetailsStr = String.format("Console: %s %nLanguage: %s", console , language);		
    		typeText.setText("VIDEOGAME");
    	}
    	label1.setText(itemDetailsStr);
    	
    	Image profileImage = new Image(seller.getProfilePicPath());
    	imgSeller.setFill(new ImagePattern(profileImage));
    	sellerText.setText(seller.getName());
    	sellerDetails.setOnMouseClicked((MouseEvent e) -> {
	        	getBundle().addBean("selectedUser", seller);   	
	            goToScene("profile");
	    });
    	
    	priceText.setText(itemInSale.getPrice().toString() + " Coins");
    	
    	setRatingsSeller();
    	setRequestView();
    	setWishlistView(itemInSale);
    	fillOtherSeller();	
    }
    
    @Override
    public void onExit() {
    	super.onExit();
    	msgLabel.setVisible(false);
    	buyBtn.setDisable(false);
    	wishlist.setDisable(true);
		wishlist.setVisible(false);
    }
    
    public void clickOnBuy() {
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/Request.fxml"));
    	loader.setController(this);
    	
    	try {
    		secondaryStage = new Stage();
    		Scene scene = new Scene(loader.load());
    		secondaryStage.setScene(scene);
    		secondaryStage.initModality(Modality.APPLICATION_MODAL);
    		secondaryStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void fillImg() {
    	imgItem.getChildren().clear();
    	for(String photo : itemDetails.getMedia()) {
    		ImageView img = new ImageView();
    		img.setImage(new Image(photo));
    		img.setFitHeight(140);
    		img.setFitWidth(140);
    		imgItem.getChildren().add(img);
    		img.setOnMouseClicked((MouseEvent e) -> imgV.setImage(img.getImage()));
    	}
    	
    }
    
    public void fillOtherSeller() {
    		List<ItemInSaleBean> itemInSale = controller.getOtherItem(seller.getUserID(), item.getItemName());
    		otherItemBox.getChildren().clear();
    		for(ItemInSaleBean itemInSaleBean: itemInSale) {
    			OtherItemCase otherItem = new OtherItemCase(itemInSaleBean);
    			otherItemBox.getChildren().add(otherItem.getBody());
    			
    			otherItem.getImg().setOnMouseClicked((MouseEvent e) -> {
    		        
    		        	getBundle().addBean("selectedItem", itemInSaleBean);
    		            goToScene("itemDetails");
    		        }
    		    );
    		}
    		
    }   
   
    public void setRequestView() {
    	if(!itemDetails.getAvailability()) {
    		msgLabel.setVisible(false);
    		buyBtn.setDisable(true);
    		return;
    	}
    	if(loggedUser == null) {
    		msgLabel.setVisible(false);
        	return;
    	}
    	
    	if( seller.getUserID().equals(loggedUser.getUserID()) ) {
    		buyBtn.setDisable(true);
    		msgLabel.setVisible(false);
    		return;
    	}
    	
    	if ( controller.checkRequest(loggedUser.getUserID(), itemDetails.getItemInSaleID()) ) {
    		buyBtn.setDisable(true);
    		msgLabel.setVisible(true);
    		msgLabel.setText("Request Already Sent");
    	}
    	else {
    		buyBtn.setDisable(false);
    		msgLabel.setVisible(false);
    	}
    		
    }
    
    public void setWishlistView(ItemInSaleBean itemInSale) {
    	if(!itemDetails.getAvailability()) {
    		wishlist.setDisable(true);
    		wishlist.setVisible(false);
    		return;
    	}
    	if(loggedUser == null) {
    		wishlist.setDisable(true);
        	wishlist.setVisible(false);
        	return;
    	}
    	if ( !seller.getUserID().equals(loggedUser.getUserID()) ) {
    		wishlist.setDisable(false);
        	wishlist.setVisible(true); 	
    	}
    	else {
    		wishlist.setDisable(true);
        	wishlist.setVisible(false);
        	return;
    	}
    	
    	if ( wishlistController.checkWishlist(loggedUser.getUserID(), itemInSale.getItemID())) {
    		wishlist.setText("In Wishlist");
    		wishlist.setDisable(true);
    		return;
    	}
    	else {
    		wishlist.setText("Add to wishlist");
    	}
    	
    	wishlist.setOnMouseClicked((MouseEvent e) -> { 
	        
	        	wishlistController.addToWishlist(loggedUser.getUserID(), itemInSale.getItemID());
	        	wishlist.setText("In Wishlist");
	        	wishlist.setDisable(true);
	        }
	    );
    		
    	
    }
    
    public void setRatingsSeller() {
    	UserProfileBean profileBean = controllerProfile.getUserProfileData(seller);
    	if(profileBean.getSellerVote() != null) {
			userR.setValue(profileBean.getSellerVote());
		}else {
			userR.setValue(0);
		}
    	userR.setEditable(false);
		userR.setPaneWidth(100f);
		sellerReviews.getChildren().clear();
		sellerReviews.getChildren().add(userR);	
    }
    
    //MEDOTI PER LA REQUEST:
    
    public void send() {
    	String buyerID = loggedUser.getUserID();
    	Integer itemInSaleID = itemDetails.getItemInSaleID();
    	String request = requestArea.getText();
    	if (controller.clickOnBuy(buyerID, itemInSaleID, request)) {
    		secondaryStage.close();
    		buyBtn.setDisable(true);
    		msgLabel.setText("Request Sent");
    		msgLabel.setVisible(true);
    	}
    	else {
    		errorLabel.setText("Error: you entered too many characters.");
    		errorLabel.setVisible(true);	
    	}
    }
    
    public void onTextChange() {
    	Integer characters = requestArea.getLength();
    	if (characters > 300 ) {
    		character.setTextFill(Color.RED);
    		character.setText(characters.toString());	
    	}
    	else if (characters >= 0 ) {
    		character.setTextFill(Color.BLACK);
        	character.setText(characters.toString());
    	}
    }
    
    
}
