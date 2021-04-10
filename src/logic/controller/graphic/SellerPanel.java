package logic.controller.graphic;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SellerPanel extends Application {
	
	public static void main(String args[]) {
		Application.launch(args);
	}

	@Override
	public void start(Stage sellerPanel) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		String filePath = "src/logic/view/SellerPanel.fxml"; // path del file di riferimento
		FileInputStream fxmlStream = new FileInputStream(filePath);
		
		VBox root = (VBox) loader.load(fxmlStream);
		
		root.getChildren().add(new Label("nodo"));
		
		// Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        sellerPanel.setScene(scene);
        // Set the Title to the Stage
        sellerPanel.setTitle("Request list");
        // Display the Stage
        sellerPanel.show();
		
		
	}

}
