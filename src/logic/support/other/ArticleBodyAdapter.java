package logic.support.other;

import javafx.scene.layout.Pane;
import logic.bean.ArticleBean;
import logic.controller.graphic.GridReviewContainer;
import logic.controller.graphic.VerticalReviewContainer;

public class ArticleBodyAdapter {

	public Pane buildArticleBody(ArticleBean article) {
		Pane body = null;
		switch(article.getLayout()) {
			case "grid":
				body = new GridReviewContainer(article).getCaseBody();
				break;
			case "linear":
				body = new VerticalReviewContainer(article).getCaseBody();
				break;
			default:
				body = new VerticalReviewContainer(article).getCaseBody();
		}
		return body;	
	}
}
