package logic.controller.graphic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.bean.ArticleBean;

public class ReviewContainer extends GraphicWidget{
	
	private Pane caseBody;
	
	public ReviewContainer(ArticleBean articleData, String layoutFile) {
		try {
			caseBody = new FXMLLoader(getClass().getResource(layoutFile)).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadComponents(caseBody);
		Text text1 = (Text) getComponent("text1");
		Text text2 = (Text) getComponent("text2");
		Text text3 = (Text) getComponent("text3");
		Text text4 = (Text) getComponent("text4");
		
		text1.setText(articleData.getText()[0]);
		text2.setText(articleData.getText()[1]);
		text3.setText(articleData.getText()[2]);
		text4.setText(articleData.getText()[3]);
		
		ArrayList<ImageView> images = new ArrayList<>();
		images.add((ImageView) getComponent("image1"));
		images.add((ImageView) getComponent("image2"));
		images.add((ImageView) getComponent("image3"));
		images.add((ImageView) getComponent("image4"));
		
		for(Integer i = 0; i < Math.min(articleData.getMediaPaths().size(), 4); i++) {
			System.out.println("set image" + i);
			System.out.println(articleData.getMediaPaths().get(i));
			
			images.get(i).setImage(new Image(articleData.getMediaPaths().get(i)));
			
		}
	}
	
	public Pane getCaseBody() {
		return caseBody;
	}
}
