package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.bean.ArticleBean;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ReviewPreviewView extends SceneManageable{

	@FXML
	private VBox reviewContainer;
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
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		ArticleBean articleData = (ArticleBean) getBundle().getBean("articleData");
		
		ReviewContainer container = new ReviewContainer(articleData);
		
		reviewContainer.getChildren().add(container.getCaseBody());
		
		
	}
	
	@Override
	public void onExit() {
		reviewContainer.getChildren().clear();
	}
	
}
