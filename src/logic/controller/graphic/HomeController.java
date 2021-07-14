package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import logic.bean.UserBean;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class HomeController extends SceneManageable{
	
	private UserBean loggedUser = null;
	
	@FXML
	public void goToSellerPanel() {
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		goToScene("sellerpanel");
		
	}
	@FXML
	public void goToWishlist() {
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		goToScene("wishlist");
	}
	
	@FXML
	public void goToReviewerPanel() {
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		goToScene("reviewerpanel");
	}
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		loggedUser = (UserBean) getBundle().getBean("loggedUser");
	}

}
