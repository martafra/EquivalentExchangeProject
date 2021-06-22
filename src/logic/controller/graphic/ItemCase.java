package logic.controller.graphic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import logic.bean.ItemBean;

public class ItemCase extends GraphicWidget{
	
	private Pane boxBody;
	private Double defaultHeight = 0d;
	private ItemBean itemData;
	
	public ItemCase(ItemBean itemData) {
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/ItemCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		defaultHeight = boxBody.getPrefHeight();
		
		Text itemName = (Text) getComponent("nameText");
		Label option1 = (Label) getComponent("option1Label");
		Label option2 = (Label) getComponent("option2Label");
		this.itemData = itemData;
		
		itemName.setText(itemData.getItemName());
		
		switch(itemData.getType()) {
			case 'B':
				option1.setText("Edition: " + itemData.getEdtion());
				option2.setText("Language: " );
				break;
			case 'M':
				option1.setText("");
				option2.setText("");
				break;
			case 'V':
				option1.setText("Console: ");
				break;
			default:
				break;	
		}
		
		
	}
	
	public Pane getPane() {
		return this.boxBody;
	}
	
	public void setVisible(Boolean isVisible) {
		
		getPane().setVisible(isVisible);
		
		if(Boolean.TRUE.equals(isVisible)) {
			getPane().setPrefHeight(defaultHeight);
		}else {
			getPane().setPrefHeight(0);
		}
		
	}
	
	public void color(Boolean color) {
		if(Boolean.TRUE.equals(color))
			boxBody.setStyle("-fx-background-color: #D7E9D8");
		else
			boxBody.setStyle("-fx-background-color: #FFFFFF");
	}

	public ItemBean getData() {
		return this.itemData;
	}
	

	
}
