package logic.controller.graphic;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.controller.application.WishlistController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class WishlistView extends SceneManageable{
	 @FXML
	 private VBox itemBox;
	 
	WishlistController controller = new WishlistController();
	UserBean loggedUser = null;
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		loggedUser = (UserBean)bundle.getBean("loggedUser");
		
		List<ItemInSaleBean> wishlist = controller.getItemInWishlist(loggedUser);
		itemBox.getChildren().clear();
		
		for(ItemInSaleBean item: wishlist) {
			WishlistCase wishlistCase = new WishlistCase(item);
			
			if (item.getAvailability() ) {
				wishlistCase.getItemLabel().setOnMouseClicked((MouseEvent e) -> {
					
						getBundle().addBean("selectedItem", item);
						goToScene("itemDetails");
					
				});
			}
			
			wishlistCase.getRemoveBtn().setOnAction((ActionEvent e) -> {
				
			        	controller.removeFromWishlist(loggedUser.getUserID(),item.getItemID());
			        	onLoad(bundle);
			        
			    });
			
			itemBox.getChildren().add(wishlistCase.getBody());
		}	
	}
}
