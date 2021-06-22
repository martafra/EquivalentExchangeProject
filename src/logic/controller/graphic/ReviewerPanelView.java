package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ReviewerPanelView extends SceneManageable{

	@FXML
	private VBox reviewContainer;
	
	@FXML
	public void goToWriteReview() {
		goToScene("writereview");
	}
	
	
	@Override
	public void onLoad(Bundle bundle){
		super.onLoad(bundle);
		
		Button button;
		reviewContainer.setPrefHeight(100000);
		
		
		for(Integer i = 0; i < 10; i++) {
			button = new Button(i.toString());
			button.setPrefWidth(300);
			button.setPrefHeight(300);
			reviewContainer.getChildren().add(button);
		}
		
		
		
	}
	
	
	
	
	
	
}
