package logic.controller.graphic;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ProfileBox extends HBox{

	private Label profileName;
	private Circle profilePic;
	
	public ProfileBox() {
		
		this.setHeight(60);
		this.setWidth(200);
		
		profileName = new Label("Prova");
		profilePic = new Circle(20);
		this.getChildren().add(profileName);
		this.getChildren().add(profilePic);
		
		profileName.setStyle("-fx-font-size: 18;");
		this.setAlignment(Pos.CENTER_RIGHT);
		
		setProfilePic("/logic/view/assets/images/logo.png");
		
		HBox.setMargin(profileName, new Insets(0,20,0,0));
		HBox.setMargin(profilePic, new Insets(0,20,0,0));
	}
	
	public void setProfileName(String username) {
		profileName.setText(username);
	}
	
	public void setProfilePic(String profilePicPath) {
		Image profileImage = new Image(profilePicPath);
		profilePic.setFill(new ImagePattern(profileImage));
	}
	public void collapse() {
		this.setVisible(false);
		this.setPrefWidth(0);
	}
	public void expand() {
		this.setVisible(true);
		this.setPrefWidth(200);
	}
	
}
