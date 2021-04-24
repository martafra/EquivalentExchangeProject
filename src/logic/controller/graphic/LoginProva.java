package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.LoginController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class LoginProva extends SceneManageable {
	
	LoginController controller = new LoginController();
	@FXML
	private Label textview;

	
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		LoginBean login = (LoginBean) this.bundle.get("loggedUser");
		UserBean loggedUser = controller.getUserByUsername(login.getUserID());
		
		textview.setText(loggedUser.getUserID() +"\n\n"+ loggedUser.getName() + " " + loggedUser.getLastName() + "\n" + loggedUser.getEmail());
	}
    
}
