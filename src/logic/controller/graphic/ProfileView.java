package logic.controller.graphic;

import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.bean.UserProfileBean;
import logic.controller.application.ProfileController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ProfileView extends SceneManageable{

	private UserBean loggedUser;
	
	@FXML
	private Label nameLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label genderLabel;
	@FXML
	private Label ageLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Circle profileImage;
	@FXML
	private ImageView coverImage;
	@FXML
	private HBox productList;
	@FXML
	private HBox guideList;
	
	private ProfileController controller = new ProfileController();
	
	@FXML
	public void goToSellerPanel() {
		goToScene("sellerpanel");
	}
	
	@FXML
	public void goToReviewerPanel() {
		goToScene("reviewerpanel");
	}
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		loggedUser = (UserBean) getBundle().getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		UserProfileBean userData = controller.getUserProfileData(loggedUser);
		
		nameLabel.setText(userData.getName() + " " + userData.getLastName());
		usernameLabel.setText(userData.getUserID());
		genderLabel.setText(userData.getGender());
		ageLabel.setText("I'm " + userData.getAge().toString() + " years old");
		cityLabel.setText(userData.getCity() + ", " + userData.getCountry());
		
		if(userData.getDescription() != null)
			descriptionLabel.setText(userData.getDescription());
		else 
			descriptionLabel.setText("There is no biography for this user");
		
		profileImage.setFill(new ImagePattern(new Image(userData.getProfilePicPath())));
		
		loadProducts(4);
	}
	
	private void loadProducts(Integer numberOfProducts) {
		
		
		productList.getChildren().clear();
		List<ItemInSaleBean> products = controller.getProductsByUser(loggedUser, numberOfProducts);
		for(ItemInSaleBean product : products) {
			ProductCase productCase = new ProductCase(product);
			productCase.getProductName().setOnMouseClicked(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedItem", product);
		            goToScene("itemDetails");
		        }
		    });
			productList.getChildren().add(productCase.getBody());
			
			
		}
		
	}
	
	
}
