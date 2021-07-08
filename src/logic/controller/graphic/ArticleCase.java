package logic.controller.graphic;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import logic.bean.ArticleBean;

public class ArticleCase extends GraphicWidget{
	
	private Pane boxBody;
	
	
	public ArticleCase(ArticleBean article){
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/ArticleCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		ImageView coverPic = (ImageView) getComponent("coverPic");
		HBox ratingBox = (HBox) getComponent("ratingBox");
		Label title = (Label) getComponent("title");
		Label author = (Label) getComponent("author");
		Label reviewsNum = (Label) getComponent("reviewsNum");
		Label articlePreview = (Label) getComponent("articlePreview");
		
		RatingView averageRating = new RatingView(5);
		averageRating.setEditable(false);
		averageRating.setPaneWidth(110f);
		averageRating.setValue(article.getVote());
		coverPic.setImage(new Image( (article.getMediaPaths()).get(0) ));
		title.setText(article.getTitle());
		author.setText(article.getAuthor().getUserID());
		ratingBox.getChildren().add(averageRating);		
		reviewsNum.setText(article.getNumberOfReviews().toString() + " reviews");
		articlePreview.setText(article.getText()[0]);
	}
	
	public void setBackgroundColor(String type) {
		if (type.equalsIgnoreCase("REVIEW")) {
			boxBody.setStyle("-fx-background-color: #D7E9D8; -fx-background-radius : 15;");
		}
		if (type.equalsIgnoreCase("GUIDE")) {
			boxBody.setStyle("-fx-background-color: #FFD9BD; -fx-background-radius : 15;");
		}
	}
	
	public Pane getBody() {
		return this.boxBody;
	}
	
}
