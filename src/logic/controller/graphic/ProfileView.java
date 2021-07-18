package logic.controller.graphic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.ArticleBean;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.bean.UserProfileBean;
import logic.controller.application.ProfileController;
import logic.controller.application.WriteReviewController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class ProfileView extends SceneManageable implements Initializable{

	private UserBean loggedUser;
	private UserProfileBean userData;
	private UserProfileBean changedUserData;
	private Boolean changed = false;
	private RatingView userR = new RatingView(5);
	private RatingView avalR = new RatingView(5);
	private RatingView relR = new RatingView(5);
	private RatingView condR = new RatingView(5);
	
	@FXML
	private HBox articleList;
	@FXML
	private Label nameLabel;
	@FXML
	private Label usernameLabel;
	@FXML
	private Label ageLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Circle profileImage;
	@FXML
	private ImageView coverImage;
	@FXML
	private HBox productList;
	@FXML
	private HBox atricleList;
	@FXML
	private HBox userReviews;
	@FXML
	private HBox reliabilityBox;
	@FXML
	private HBox availabilityBox;
	@FXML
	private HBox conditionsBox;
	@FXML
	private Button saveButton;
	@FXML
	private ScrollPane moderatorRequests;
	@FXML
	private VBox reviewAcceptBox;
	@FXML
	private Label sellerPanelLabel;
	@FXML
	private Label reviewerPanelLabel;
	@FXML
	private Label modifyDescriptionLabel;
	@FXML
	private Button modifyCover;
	@FXML
	private Button modifyPicture;
	
	
	private ProfileController controller = new ProfileController();
	private WriteReviewController modController = new WriteReviewController();
	
	@FXML
	public void goToSellerPanel() {
		goToScene("sellerpanel");
	}
	
	@FXML
	public void goToReviewerPanel() {
		goToScene("reviewerpanel");
	}
	@FXML
	public void changeProfilePic() throws FileNotFoundException{
		String profilePicPath = getPicture();
		if(profilePicPath != null) {
			profileImage.setFill(new ImagePattern(new Image(new FileInputStream(profilePicPath))));
			changedUserData.setProfilePicPath(profilePicPath);
			changed = true;
		}
		saveButton.setVisible(changed);
	}
	@FXML
	public void changeCoverPic() throws FileNotFoundException{
		String coverPicPath = getPicture();
		if(coverPicPath != null) {
			coverImage.setImage(new Image(new FileInputStream(coverPicPath)));
			changedUserData.setCoverPicPath(coverPicPath);
			changed = true;
		}
		saveButton.setVisible(changed);
	}
	@FXML
	public void saveProfile() {
		controller.updateUserProfileData(changedUserData);
		changed = false;
		saveButton.setVisible(changed);
	}
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		loggedUser = (UserBean) getBundle().getBean("loggedUser");
		UserBean selectedUser = (UserBean) getBundle().getBean("selectedUser");
		
		
		
		
		if(selectedUser != null) {
			if(loggedUser != null && !selectedUser.getUserID().equals(loggedUser.getUserID())) {
				this.moderatorRequests.setVisible(false);
				this.modifyDescriptionLabel.setVisible(false);
				this.sellerPanelLabel.setVisible(false);
				this.reviewerPanelLabel.setVisible(false);
				this.modifyPicture.setVisible(false);
				this.modifyCover.setVisible(false);
			}
			loggedUser = selectedUser;
		}
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		if(Boolean.TRUE.equals(loggedUser.isModerator())) {
			setupModeratorView();
		}
		
		userData = controller.getUserProfileData(loggedUser);
		changedUserData = new UserProfileBean();
		changedUserData.setUserID(loggedUser.getUserID());
		changed = false;
		saveButton.setVisible(changed);
		nameLabel.setText(userData.getName() + " " + userData.getLastName());
		usernameLabel.setText(userData.getUserID());
		
		setRatings();
		
		ageLabel.setText("I'm " + userData.getAge().toString() + " years old");
		
		if(userData.getDescription() != null)
			descriptionLabel.setText(userData.getDescription());
		else 
			descriptionLabel.setText("There is no biography for this user");
		
		profileImage.setFill(new ImagePattern(new Image(userData.getProfilePicPath())));
		
		loadProducts(4);
		loadArticles(4);
	}
	
	@Override 
	public void onExit() {
		reviewAcceptBox.getChildren().clear();
		getBundle().addBean("selectedUser", null);
		this.moderatorRequests.setVisible(true);
		this.modifyDescriptionLabel.setVisible(true);
		this.sellerPanelLabel.setVisible(true);
		this.reviewerPanelLabel.setVisible(true);
		this.modifyPicture.setVisible(true);
		this.modifyCover.setVisible(true);
	}
	
	private void loadProducts(Integer numberOfProducts) {
		productList.getChildren().clear();
		List<ItemInSaleBean> products = controller.getProductsByUser(loggedUser, numberOfProducts);
		for(ItemInSaleBean product : products) {
			ProductCase productCase = new ProductCase(product);
			productCase.getProductName().setOnMouseClicked((MouseEvent e) -> {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedItem", product);
		            goToScene("itemDetails");
		    });
			productList.getChildren().add(productCase.getBody());	
		}	
	}
	
	private void loadArticles(Integer numberOfArticles) {
		articleList.getChildren().clear();
		List<ArticleBean> articles = controller.getArticlesByUser(loggedUser, numberOfArticles);
		for(ArticleBean article : articles) {
			ProfileArticleCase profArticleCase = new ProfileArticleCase(article);
			profArticleCase.getComponent("articleTitle").setOnMouseClicked((MouseEvent e) -> {
		        	Bundle bundle = getBundle();
		        	bundle.addBean("selectedArticle", article);
		            goToScene("article");
		    });
			articleList.getChildren().add(profArticleCase.getBody());
			
		}	
	}
	
	private String getPicture() {
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Resource File");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		 File selectedFile = fileChooser.showOpenDialog(null);
		 if(selectedFile != null) {
			 return selectedFile.getAbsolutePath();
		 }
		 return null;
	}
	
	private void setRatings(){
		
		if(userData.getSellerVote() != null) {
			userR.setValue(userData.getSellerVote());
		}else {
			userR.setValue(0);
		}
		if(userData.getOverallAvailabilityValue() != null) {
			avalR.setValue(userData.getOverallAvailabilityValue());
		}else {
			avalR.setValue(0);
		}
		if(userData.getOverallReliabiltyValue() != null) {
			relR.setValue(userData.getOverallReliabiltyValue());
		}else {
			relR.setValue(0);
		}
		if(userData.getOverallConditionsValue() != null) {
			condR.setValue(userData.getOverallConditionsValue());
		}else {
			condR.setValue(0);
		}

	}
	
	private void setupModeratorView() {
		
		moderatorRequests.setVisible(true);
		
		List<ArticleBean> articles = modController.getPendingArticles();
		
		for(ArticleBean article : articles) {
			ArticleAcceptanceBox articleCase = new ArticleAcceptanceBox(article);
			reviewAcceptBox.getChildren().add(articleCase.getPane());
			articleCase.getToButton().setOnAction((ActionEvent e) -> {
				getBundle().addBean("articleData", article);
				goToScene("reviewpreview");
			});
			
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userR.setEditable(false);
		userR.setPaneWidth(100f);
		userReviews.getChildren().add(userR);
		avalR.setEditable(false);
		avalR.setPaneWidth(100f);
		availabilityBox.getChildren().add(avalR);
		relR.setEditable(false);
		relR.setPaneWidth(100f);
		reliabilityBox.getChildren().add(relR);
		condR.setEditable(false);
		condR.setPaneWidth(100f);
		conditionsBox.getChildren().add(condR);
		moderatorRequests.setVisible(false);
	}
	
	
}
