package logic.controller.graphic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import logic.bean.UserBean;
import logic.controller.application.LoginController;
import logic.support.interfaces.SceneManageable;
import logic.support.other.LoginSession;
import logic.support.other.PaneManager;

public class LoginProva implements SceneManageable {
	
	PaneManager myManager;
	LoginController controller = new LoginController();
	@FXML
	private Label textview;
	

	
	@Override
	public void setPaneManager(PaneManager manager) {
		myManager = manager;
	}

	@Override
	public void onLoad() {
		String username = LoginSession.getInstance().getLoginSessionID();
		UserBean loggedUser = controller.getLoggedUser();
		
		textview.setText(loggedUser.getUserID() +"\n\n"+ loggedUser.name + " " + loggedUser.lastName + "\n" + loggedUser.email);
	}
    
}
