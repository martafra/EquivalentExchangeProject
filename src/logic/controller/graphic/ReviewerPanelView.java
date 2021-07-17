package logic.controller.graphic;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
		for(ArticleBean art: articles) {
			ArticleCase artCase = new ArticleCase(art);
			artCase.getComponent("title").setOnMouseClicked((MouseEvent e) -> {
		       
				getBundle().addBean("selectedArticle", art);
		        goToScene("article");
		        
		    });
			artCase.getComponent("author").setOnMouseClicked((MouseEvent e) -> {
		  	
				getBundle().addBean("loggedUser", art.getAuthor());
		        goToScene("profile");
		        
			});
			
			Node removeArt = artCase.getComponent("removeArticle");
			removeArt.setVisible(true);
			removeArt.setDisable(false);
			removeArt.setOnMouseClicked((MouseEvent e) -> {
				writeController.removeArticle(art);
		        goToScene("reviewerpanel");
			});
			
			artCase.setBackgroundColor(art.getType());
			if(art.getType().equalsIgnoreCase("review")){
				reviewsBox.getChildren().add(artCase.getBody());
			}
			else{
				guidesBox.getChildren().add(artCase.getBody());
			}
		}
	}
	
}
