package logic.controller.graphic;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.bean.ArticleBean;

public class ProfileArticleCase extends GraphicWidget{
	
	private Pane profArticleCase;
	
	public ProfileArticleCase(ArticleBean article){
		try {
			profArticleCase = new FXMLLoader(getClass().getResource("/logic/view/ProfileArticleCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(profArticleCase);
		
		ImageView articlePreview = (ImageView) getComponent("articlePreview");
		Label title = (Label) getComponent("articleTitle");
		
		articlePreview.setImage(new Image( (article.getMediaPaths()).get(0) ));
		title.setText(article.getTitle());
	}
	
	public Pane getBody() {
		return this.profArticleCase;
	}
}



