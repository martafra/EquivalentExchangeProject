package logic.controller.graphic;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.bean.RequestBean;

public class RequestCase extends GraphicWidget{
	
	private Pane caseBody;
	
	public RequestCase(RequestBean itemData) {
		
		try {
			caseBody = new FXMLLoader(getClass().getResource("/logic/view/RequestCase.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadComponents(caseBody);
		
		ImageView requestPic = (ImageView) getComponent("requestPic");
		requestPic.setImage(new Image(itemData.getReferredItemBean().getMediaPath()));
		
		Label requestItem = (Label) getComponent("requestItem");
		requestItem.setText(itemData.getReferredItemBean().getItemName());
		
		Label requestBuyer = (Label) getComponent("requestBuyer");
		requestBuyer.setText(itemData.getBuyer());
		
		Label requestNote = (Label) getComponent("requestNote");
		requestNote.setText(itemData.getNote());
		
	}
	
	public Pane getBody() {
		return this.caseBody;
	}
	
	public Button getAcceptButton() {
		return (Button) this.getComponent("requestAcc");
	}
	
	public Button getRejectButton() {
		return (Button) this.getComponent("requestRej");
	}
	
	public Label getItemLabel() {
		return (Label) this.getComponent("requestItem");
	}
	
	public Label getBuyerLabel() {
		return (Label) this.getComponent("requestBuyer");
	}
	
}
