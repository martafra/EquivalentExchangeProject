package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import logic.support.other.SceneManageable;

public class ReviewPreviewView extends SceneManageable{

	@FXML
	private Pane reviewContainer;
	@FXML
	private Button saveButton;
	@FXML
	private Button backButton;
	
	@FXML
	public void saveReview() {
		System.out.println("Hello");
	}
	
	@FXML
	public void backToEditor() {
		goToScene("writereview");
	}
	
}
