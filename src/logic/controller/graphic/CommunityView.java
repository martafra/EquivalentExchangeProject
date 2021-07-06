package logic.controller.graphic;

import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import logic.bean.ArticleBean;
import logic.controller.application.CommunityController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class CommunityView extends SceneManageable{
	
	@FXML
	private FlowPane articlePane;
	@FXML
	private TextField searchBar;
	@FXML
	private Button allBtn;
	@FXML
	private Button booksBtn;
	@FXML
	private Button vgGuidesBtn;
	@FXML
	private Button vgReviewsBtn;
	@FXML
	private Button moviesBtn;
	@FXML
	private Button searchBtn;
	
	private CommunityController comController = new CommunityController();
	
	@Override 
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		List<ArticleBean> articles = comController.getAllAcceptedArticles();
		for(ArticleBean article: articles) {
			ArticleCase articleCase = new ArticleCase(article);
			articleCase.getComponent("title").setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedArticle", article);
		            goToScene("article");
		        }
		    });
			articleCase.getComponent("author").setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedUser", article.getAuthor());
		            goToScene("article");
		        }
		    });
			articleCase.setBackgroundColor(article.getType());
			articlePane.getChildren().add(articleCase.getBody());
		}
	}
	
	@Override
	public void onExit() {
		super.onExit();
		articlePane.getChildren().clear();
	}
}
