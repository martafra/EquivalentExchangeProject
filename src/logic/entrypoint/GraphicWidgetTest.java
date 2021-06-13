package logic.entrypoint;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.controller.graphic.ChatBox;

public class GraphicWidgetTest extends Application{
	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		ChatBox box = new ChatBox(new UserBean());
		
	}
}
