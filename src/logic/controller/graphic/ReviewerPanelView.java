package logic.controller.graphic;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;
import javafx.scene.input.MouseEvent;
import logic.bean.UserBean;
import logic.bean.ArticleBean;
import logic.controller.application.CommunityController;
import logic.controller.application.WriteReviewController;

public class ReviewerPanelView extends SceneManageable{

	@FXML
	private VBox reviewsBox;
	@FXML
	private VBox guidesBox;
	@FXML
	private Button writeReviewButton;
	
	@FXML
	public void goToWriteReview() {
		goToScene("writereview");
	}
	
	private CommunityController comController = new CommunityController();
	private WriteReviewController writeController = new WriteReviewController();
	
	@Override
	public void onLoad(Bundle bundle){
		super.onLoad(bundle);
		
		UserBean loggedUser = (UserBean) getBundle().getBean("loggedUser");
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		List<ArticleBean> articles = comController.getAllAcceptedArticles(loggedUser);
		loadCases(articles);		
	}	
	
	@Override 
	public void onExit(){
		super.onExit();
		reviewsBox.getChildren().clear();
		guidesBox.getChildren().clear();
	}
	private void loadCases(List<ArticleBean> articles) {
		for(ArticleBean article: articles) {
			ArticleCase articleCase = new ArticleCase(article);
			articleCase.getComponent("title").setOnMouseClicked((MouseEvent e) -> {
		       
				getBundle().addBean("selectedArticle", article);
		        goToScene("article");
		        
		    });
			articleCase.getComponent("author").setOnMouseClicked((MouseEvent e) -> {
		  	
				getBundle().addBean("loggedUser", article.getAuthor());
		        goToScene("profile");
		        
			});
			articleCase.getComponent("removeArticle").setVisible(true);
			articleCase.getComponent("removeArticle").setDisable(false);
			articleCase.getComponent("removeArticle").setOnMouseClicked((MouseEvent e) -> {
				writeController.removeArticle(article);
		        goToScene("reviewerpanel");
			});
			articleCase.setBackgroundColor(article.getType());
			if(article.getType().equalsIgnoreCase("review")){
				reviewsBox.getChildren().add(articleCase.getBody());
			}
			else{
				guidesBox.getChildren().add(articleCase.getBody());
			}
		}
	}
	
}
