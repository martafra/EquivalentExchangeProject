package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.bean.ArticleBean;
import logic.bean.UserBean;
import logic.controller.application.WriteReviewController;
import logic.support.other.ArticleBodyAdapter;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ReviewPreviewView extends SceneManageable{

	private WriteReviewController controller = new WriteReviewController();
	private ArticleBean articleData;
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
	private Label reviewTitle;
	
	
	@FXML
	public void acceptReview() {
		controller.acceptArticle(articleData);
		goToScene("profile");
	}
	@FXML
	public void rejectReview() {
		controller.rejectArticle(articleData);
		goToScene("profile");
	}
	
	@FXML
	public void saveReview() {

		articleData = (ArticleBean) getBundle().getBean("articleData");
		controller.saveArticle(articleData);
		
		goToScene("reviewerpanel");
	}
	
	@FXML
	public void backToEditor() {
		goToScene("writereview");
	}
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		articleData = (ArticleBean) getBundle().getBean("articleData");
		reviewTitle.setText(articleData.getTitle());
		ArticleBodyAdapter adapter = new ArticleBodyAdapter();
		Pane container = adapter.buildArticleBody(articleData);
		
		reviewContainer.getChildren().add(container);
		
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
