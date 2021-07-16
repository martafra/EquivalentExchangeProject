package logic.entrypoint;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.controller.graphic.HeaderBar;
import logic.support.other.ImageCache;
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
		mainPanel.loadScene("reviewerpanel", "/logic/view/ReviewerPanel.fxml");
		mainPanel.loadScene("itemDetails", "/logic/view/ItemDetails.fxml");
		mainPanel.loadScene("wallet", "/logic/view/Wallet.fxml");
		mainPanel.loadScene("ordersummary", "/logic/view/OrderSummary.fxml");
		mainPanel.loadScene("profile", "/logic/view/Profile.fxml");
		mainPanel.loadScene("writereview", "/logic/view/WriteReview.fxml");
		mainPanel.loadScene("wishlist", "/logic/view/WishList.fxml");
		mainPanel.loadScene("reviewpreview", "/logic/view/ReviewPreview.fxml");
		mainPanel.loadScene("article", "/logic/view/ReadArticle.fxml");

		mainPanel.loadHeaderBar("/logic/view/HeadBar.fxml");
		mainPanel.loadMenu("/logic/view/Menu.fxml");
		
		mainPanel.setScene("home");
		
		var root = new VBox();
		Parent headerBarContent = (Parent) mainPanel.getHeaderContent();
		
		root.getChildren().add(headerBarContent);
		root.getChildren().add(mainPanel);
		
			
		var scene = new Scene(root);
		Font.loadFont(getClass().getResourceAsStream("/logic/view/assets/fonts/Spartan-Regular.ttf"), 22);
		
		stage.setOnCloseRequest((WindowEvent e) -> {
	             stage.close();
	             ((HeaderBar)mainPanel.getHeaderController()).logout();
	             ImageCache.getInstance().remove();
	     });        
		scene.getStylesheets().add(getClass().getResource("/logic/view/style/Style.css").toExternalForm());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();

		
	}
	
	

}
