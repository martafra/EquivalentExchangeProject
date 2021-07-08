package logic.controller.graphic;

import logic.bean.ArticleBean;

public class GridReviewContainer extends ReviewContainer{

	public GridReviewContainer(ArticleBean articleData) {
		
		super(articleData, "/logic/view/reviewLayouts/RLayout1.fxml");
	}

}
