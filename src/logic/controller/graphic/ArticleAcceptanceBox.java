package logic.controller.graphic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import logic.bean.ArticleBean;

public class ArticleAcceptanceBox extends GraphicWidget{
	
	private Pane boxBody;
	private ArticleBean article = new ArticleBean();
	
	public ArticleAcceptanceBox(ArticleBean articleData) {
		
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/ReviewAcceptanceRequest.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		article = articleData;
		
		Label userText = (Label) getComponent("userLabel");
		Label titleText = (Label) getComponent("titleLabel");
		
	}
	
	public Pane getPane() {
		return this.boxBody;
	}
	
	public Button getToButton() {
		return (Button) getComponent("goToReviewButton");
	}
	
	public ArticleBean getArticleData() {
		return article;
	}
	
}
