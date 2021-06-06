package logic.entrypoint;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.support.other.PaneManager;

public class MainApplication extends Application {	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception{
		
		var mainPanel = new PaneManager();
		mainPanel.loadScene("login", "/logic/view/LoginView.fxml");
		mainPanel.loadScene("register", "/logic/view/Registration.fxml");
		mainPanel.loadScene("home", "/logic/view/Home.fxml");
		mainPanel.loadScene("community", "/logic/view/Community.fxml");
		mainPanel.loadScene("catalogue", "/logic/view/Catalogue.fxml");
		mainPanel.loadScene("chat", "/logic/view/Chat.fxml");
		mainPanel.loadScene("postad", "/logic/view/PostAd.fxml");
		mainPanel.loadScene("sellerpanel", "/logic/view/SellerPanel.fxml");
		
		mainPanel.loadHeaderBar("/logic/view/HeadBar.fxml");
		
		mainPanel.setScene("home");
		
		var root = new VBox();
		Parent headerBarContent = (Parent) mainPanel.getHeaderContent();
		
		
		root.getChildren().add(mainPanel);
		root.getChildren().add(0, headerBarContent);
		
		
		var scene = new Scene(root);
		Font.loadFont(getClass().getResourceAsStream("/logic/view/assets/fonts/Spartan-Regular.ttf"), 22);
		scene.getStylesheets().add(getClass().getResource("/logic/view/style/Style.css").toExternalForm());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();

		
	}
	
	

}
