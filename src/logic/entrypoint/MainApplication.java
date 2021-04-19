package logic.entrypoint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.support.other.PaneManager;

public class MainApplication extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		PaneManager mainPanel = new PaneManager();
		mainPanel.loadScene("login", "/logic/view/LoginView.fxml");
		mainPanel.loadScene("loginprova", "/logic/view/LoginProva.fxml");
		
		mainPanel.setScene("login");
		
		VBox root = new VBox();
		
		root.getChildren().add(mainPanel);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}

}
