package logic.controller.graphic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.bean.ChatBean;

public class MessageCase extends GraphicWidget{

	private Pane boxBody;
	private DateFormat format = new SimpleDateFormat("HH:mm");
	
	
	public MessageCase(ChatBean messageData) {
		try {
			boxBody = new FXMLLoader(getClass().getResource("/logic/view/MessageBox.fxml")).load();
		} catch (IOException e) {
			e.printStackTrace();	
		}
		
		loadComponents(boxBody);
		
		Text textMessage = (Text) getComponent("textMessage");
		Label date = (Label) getComponent("date");
		
		textMessage.setText(messageData.getMessageText());
		date.setText(format.format(messageData.getDate()));
	}
	
	public Pane getBody() {
		return this.boxBody;
	}
}
