package logic.controller.graphic;




import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import logic.bean.ItemBean;
import logic.bean.ItemDetailsBean;
import logic.bean.ItemInSaleBean;
import logic.bean.OrderBean;
import logic.bean.UserBean;
import logic.controller.application.ItemDetailsController;
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
	private ItemDetailsController controller = new ItemDetailsController();
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
	
	
	
    
    @Override
    public void onLoad(Bundle bundle) {
    	
    	super.onLoad(bundle);
    	
    	loggedUser = (UserBean)bundle.getBean("loggedUser");
    	ItemInSaleBean itemInSale = (ItemInSaleBean)bundle.getBean("selectedItem");
    	itemDetails =  controller.getItemDetails(itemInSale.getItemID());
    	
    	System.out.println(itemDetails);
    	
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
    		itemDetails = item.getConsole(); //TODO prendere dalla bean
    		typeText.setText("VIDEOGAME");
    	}
    	label1.setText(itemDetails);
    	
    	Image profileImage = new Image(seller.getProfilePicPath());
    	imgSeller.setFill(new ImagePattern(profileImage));
    	sellerText.setText(seller.getName());
    	
    	priceText.setText(itemInSale.getPrice().toString() + " Coins");
    	
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
			 //((Pane) vbox.getParent()).getChildren().add(loader.load());
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
    		img.setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	//apre una finestra popUp
		        	/*Stage stage = new Stage();
		        	VBox vbox = new VBox();
		    		Scene scene = new Scene(vbox,600,600);
		    		vbox.getChildren().add(img);
		    		stage.setScene(scene);
		    		stage.initModality(Modality.APPLICATION_MODAL);
		    		stage.showAndWait();*/
		    		
		        	imgV.setImage(img.getImage());
		        
		        }
		    });
    	}
    	
    }
    
    public void fillOtherSeller() {
    		List<ItemInSaleBean> itemInSale = controller.getOtherItem(seller.getUserID(), item.getItemName());
    		otherItemBox.getChildren().clear();
    		for(ItemInSaleBean item: itemInSale) {
    			OtherItemCase otherItem = new OtherItemCase(item);
    			otherItemBox.getChildren().add(otherItem.getBody());
    			
    			otherItem.getImg().setOnMouseClicked(new EventHandler<MouseEvent>() {
    		        @Override
    		        public void handle(MouseEvent event) {
    		        	Bundle bundle = getBundle();
    		        	bundle.addBean("selectedItem", item);
    		            goToScene("itemDetails");
    		        }
    		    });
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
    	
    	wishlist.setOnMouseClicked(new EventHandler<MouseEvent>() { 
	        @Override
	        public void handle(MouseEvent event) {
	        	wishlistController.addToWishlist(loggedUser.getUserID(), itemInSale.getItemID());
	        	wishlist.setText("In Wishlist");
	        	wishlist.setDisable(true);
	        }
	    });
    		
    	
    }
    
    //MEDOTI PER LA REQUEST:
    
    public void send() {
    	String buyerID = loggedUser.getUserID();
    	Integer itemInSaleID = itemDetails.getItemInSaleID();
    	String request = requestArea.getText();
    	controller.clickOnBuy(buyerID, itemInSaleID, request); 
    	secondaryStage.close();
    	buyBtn.setDisable(true);
    	msgLabel.setText("Request Sent");
    	msgLabel.setVisible(true);
    	//((Pane) vbox.getParent()).getChildren().remove(1);
    }
    
    /*public void back() {
    	((Pane) vbox.getParent()).getChildren().remove(1);
    }*/
    
    public void onTextChange() {
    	Integer charactersLeft = maxCharacter - requestArea.getLength();
    	
    	if (charactersLeft > 0 ) {
    		character.setTextFill(Color.BLACK);
        	character.setText(charactersLeft.toString());
    	}
    	else if (charactersLeft == 0 ) {
    		character.setTextFill(Color.RED);
    		character.setText(charactersLeft.toString());	
    	}
    	else {
    		var x = requestArea.getText().substring(0, maxCharacter);
    		requestArea.setText(x);
    		requestArea.positionCaret(maxCharacter);
    	}
    }
    
    
}
