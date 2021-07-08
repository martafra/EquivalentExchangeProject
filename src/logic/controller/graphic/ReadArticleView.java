package logic.controller.graphic;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.bean.ArticleBean;
import logic.bean.UserBean;
import logic.controller.application.ArticleRetrievalController;
import logic.support.other.ArticleBodyAdapter;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ReadArticleView extends SceneManageable implements Initializable{

	private ArticleRetrievalController controller = new ArticleRetrievalController();
	private UserBean loggedUser = null;
	private ArticleBean articleData = null;
	private RatingView rating = new RatingView(5);
	private ArticleBodyAdapter adapter = new ArticleBodyAdapter();
	
	@FXML
	private Label reviewTitle;
	
	@FXML
	private Label authorName;
	
	@FXML
	private Pane ratingContainer;
	
	@FXML
	private VBox reviewContainer;
	
	@FXML
	private HBox rateBox;
	
	@FXML
	private Button rateButton;
	
	@FXML
	private Label rateReviewLabel;
	
	@FXML
	public void rateArticle() {
		
		if(rating.getValue() > 0) {
			controller.rateArticle(loggedUser, articleData, rating.getValue());
			goToScene("article");
		}
		
	}
	
	@Override 
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		articleData = (ArticleBean) getBundle().getBean("selectedArticle");
		
		loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		setRatingCase();
		
		reviewTitle.setText(articleData.getTitle());
		authorName.setText(articleData.getAuthor().getUserID());
		
		reviewContainer.getChildren().add(adapter.buildArticleBody(articleData));
		
		
	}
	
	@Override
	public void onExit() {
		
		rateBox.setVisible(true);
		rating.setEditable(true);
		rateButton.setDisable(false);
		reviewContainer.getChildren().clear();
		
	}
	
	
	
	private void setRatingCase() {
		if(loggedUser == null) {
			rateBox.setVisible(false);
			rating.setEditable(false);
			rateButton.setDisable(true);
			return;
		}
		
		if(Boolean.TRUE.equals(controller.alreadyVoted(loggedUser, articleData))) {
			rateReviewLabel.setText("Already voted");
			rating.setEditable(false);
			rateButton.setDisable(true);
			rating.setValue(0);
		}
		
		if(articleData.getAuthor().getUserID().equals(loggedUser.getUserID())) {
			rateBox.setVisible(false);
			rating.setEditable(false);
			rateButton.setDisable(true);
		}
			
	}
		

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ratingContainer.getChildren().add(rating);
		rating.setPaneWidth(150f);
	}
	
	
}
