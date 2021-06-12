package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.bean.UserBean;
import logic.controller.application.WalletController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class WalletView extends SceneManageable{
	
	 @FXML
	 private VBox orderBox;
	 @FXML
	 private Label creditLabel;
	 
	 private WalletController walletController = new WalletController();
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean)bundle.getBean("loggedUser");
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		creditLabel.setText(walletController.getCredit(loggedUser).toString());
		
		
	}
	
	
}
