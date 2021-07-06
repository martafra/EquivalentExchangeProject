package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.bean.ArticleBean;
import logic.bean.UserBean;
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
	private Button acceptReviewButton;
	@FXML
	private Button rejectReviewButton;
	
	@FXML
	public void acceptReview() {
		
	}
	@FXML
	public void rejectReview() {
		
	}
	
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
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		ArticleBean articleData = (ArticleBean) getBundle().getBean("articleData");
		
		ReviewContainer container = new ReviewContainer(articleData);
		
		reviewContainer.getChildren().add(container.getCaseBody());
		
		Boolean isAuthor = loggedUser.getUserID().equals(articleData.getAuthor().getUserID());
		Boolean isModAndNotAuthor = loggedUser.isModerator() && !isAuthor;
		System.out.println(isModAndNotAuthor);
		
		acceptReviewButton.setVisible(isModAndNotAuthor);
		acceptReviewButton.setDisable(!isModAndNotAuthor);
		rejectReviewButton.setVisible(isModAndNotAuthor);
		rejectReviewButton.setDisable(!isModAndNotAuthor);
		
		saveButton.setVisible(isAuthor);
		saveButton.setDisable(!isAuthor);
		backButton.setVisible(isAuthor);
		backButton.setDisable(!isAuthor);
		
	}
	
	@Override
	public void onExit() {
		reviewContainer.getChildren().clear();
	}
	
}
