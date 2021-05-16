package logic.entrypoint;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.controller.graphic.HeaderBar;
import logic.support.other.HeaderController;
import logic.support.other.PaneManager;

public class MainApplication extends Application {


	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception{
		
		var mainPanel = new PaneManager();
		mainPanel.loadScene("login", "/logic/view/LoginView.fxml");
		mainPanel.loadScene("loginprova", "/logic/view/LoginProva.fxml");
		mainPanel.loadScene("register", "/logic/view/Registration.fxml");
		mainPanel.loadScene("home", "/logic/view/Home.fxml");
		mainPanel.loadScene("community", "/logic/view/Community.fxml");
		mainPanel.loadScene("catalogue", "/logic/view/Catalogue.fxml");
		mainPanel.loadHeaderBar("/logic/view/HeadBar.fxml");
		
		mainPanel.setScene("login");
		
		var root = new VBox();
		Parent headerBarContent = (Parent) mainPanel.getHeaderContent();
		
		
		root.getChildren().add(headerBarContent);
		root.getChildren().add(mainPanel);
		
		var scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		
	}
	
	

}
