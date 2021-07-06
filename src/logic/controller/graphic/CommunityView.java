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
	
	private Button selectedTab;
	
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
	
	@FXML
	public void allAction(){
		List<ArticleBean> articles = comController.getAllAcceptedArticles();
		articlePane.getChildren().clear();
		selectedTab = allBtn;
		setSelectedTabStyle();
		loadCases(articles);
	}
	
	@FXML
	public void booksAction(){
		List<ArticleBean> articles = comController.getBookReviews();
		articlePane.getChildren().clear();
		selectedTab = booksBtn;
		setSelectedTabStyle();
		loadCases(articles);
	}
	
	@FXML
	public void vgGuidesAction(){
		List<ArticleBean> articles = comController.getVideogameGuides();
		articlePane.getChildren().clear();
		selectedTab = vgGuidesBtn;
		setSelectedTabStyle();
		loadCases(articles);
	}
	
	@FXML
	public void vgReviewsAction(){
		List<ArticleBean> articles = comController.getVideogameReviews();
		articlePane.getChildren().clear();
		selectedTab = vgReviewsBtn;
		setSelectedTabStyle();
		loadCases(articles);
	}
	
	@FXML
	public void moviesAction(){
		List<ArticleBean> articles = comController.getMovieReviews();
		articlePane.getChildren().clear();
		selectedTab = moviesBtn;
		setSelectedTabStyle();
		loadCases(articles);
	}
	
	@Override 
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		List<ArticleBean> articles = comController.getAllAcceptedArticles();
		loadCases(articles);
	}
	
	@Override
	public void onExit() {
		super.onExit();
		articlePane.getChildren().clear();
	}
	
	private void loadCases(List<ArticleBean> articles) {
		for(ArticleBean article: articles) {
			ArticleCase articleCase = new ArticleCase(article);
			articleCase.getComponent("title").setOnMouseClicked((MouseEvent e) -> {
		       
				getBundle().addBean("selectedArticle", article);
		        goToScene("article");
		        
		    });
			articleCase.getComponent("author").setOnMouseClicked((MouseEvent e) -> {
		  	
				getBundle().addBean("selectedUser", article.getAuthor());
		        goToScene("profile");
		        
			});
			articleCase.setBackgroundColor(article.getType());
			articlePane.getChildren().add(articleCase.getBody());
		}
	}
	
	private void setSelectedTabStyle() {
		
		setStyle(allBtn);
		setStyle(booksBtn);
		setStyle(vgGuidesBtn);
		setStyle(vgReviewsBtn);
		setStyle(moviesBtn);
	}
	
	private void setStyle(Button btn) {
		if(btn.equals(selectedTab)) {
			btn.setStyle("-fx-background-color: #D7E9D8");
		}
		else {
			btn.setStyle("-fx-background-color: #FFFFFF");
		}
	}
}
