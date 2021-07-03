package logic.controller.graphic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.ArticleBean;
import logic.bean.ItemBean;
import logic.bean.UserBean;
import logic.controller.application.WriteReviewController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class WriteReviewView extends SceneManageable implements Initializable{

	private FileChooser fileChooser;
	private ArticleBean article = new ArticleBean();
	private WriteReviewController controller = new WriteReviewController();
	private ArrayList<String> categories = new ArrayList<>();
	private Integer categoryPosition = 0;
	private ArrayList<String> types = new ArrayList<>();
	private Integer typePosition = 0;
	private UserBean author;
	private List<ItemBean> items;
	
	@FXML
	private TextArea textPanel1;
	@FXML
	private TextArea textPanel2;
	@FXML
	private TextArea textPanel3;
	@FXML
	private TextArea textPanel4;
	@FXML
	private VBox imageBox;
	@FXML
	private TextField titleTextField;
	@FXML
	private Label categoryChooserLabel;
	@FXML
	private Label typeChooserLabel;
	@FXML
	private ComboBox<ItemBean> itemComboBox;
	@FXML
	private TextField tagField;
	@FXML
	private FlowPane tagsContainer;
	
	@FXML
	public void onCategorySwapLeft() {
		categoryPosition = (categoryPosition + categories.size() - 1) % categories.size();
		categoryChooserLabel.setText(categories.get(categoryPosition));
		loadItems(categories.get(categoryPosition));
	}
	@FXML
	public void onCategorySwapRight() {
		categoryPosition = (categoryPosition + 1) % categories.size();
		categoryChooserLabel.setText(categories.get(categoryPosition));
		loadItems(categories.get(categoryPosition));
	}
	@FXML
	public void onTypeSwapLeft() {
		typePosition = (typePosition + types.size() - 1) % types.size();
		typeChooserLabel.setText(types.get(typePosition));
	}
	@FXML
	public void onTypeSwapRight() {
		typePosition = (typePosition + 1) % types.size();
		typeChooserLabel.setText(types.get(typePosition));
	}
	@FXML
	public void addTag() {
		
		if(tagField.getText().equals(""))
			return;
		
		Button tagButton = new Button(tagField.getText()
				.substring(0, Math.min(tagField.getText().length(), 20)));
		tagButton.getStyleClass().add("green-clickable");
		tagButton.setStyle("-fx-border-radius: 15");
		final String tagText = tagField.getText();
		tagButton.setOnAction((ActionEvent e) -> {
			tagsContainer.getChildren().remove(tagButton);
			article.removeTag(tagText);
		});
		
		article.addTag(tagText);
		tagsContainer.getChildren().add(tagButton);
		tagField.setText("");
	}
	@FXML
	public void viewPreview() {
		
		article.setTitle(titleTextField.getText());
		article.setAuthor(author);
		article.setCategory(categoryChooserLabel.getText());
		article.setType(typeChooserLabel.getText());
		article.setText(0, textPanel1.getText());
		article.setText(1, textPanel2.getText());
		article.setText(2, textPanel3.getText());
		article.setText(3, textPanel4.getText());
		
		article.setReferredItem(itemComboBox.getSelectionModel().getSelectedItem());
		getBundle().addBean("articleData", article);
		goToScene("reviewpreview");
	}
	
	@FXML
	public void addImage() {
		File selectedImage = fileChooser.showOpenDialog(null);
		if(selectedImage == null)
			return;
		
		String selectedImagePath = selectedImage.getAbsolutePath();
		try {
			ImageView image = new ImageView(new Image(new FileInputStream(selectedImagePath)));
			image.setFitWidth(225.0);
			image.setPreserveRatio(true);
			image.setOnMouseClicked((MouseEvent e) -> {
				imageBox.getChildren().remove(image);
				article.removeMedia(selectedImagePath);
			});
			
			imageBox.getChildren().add(image);
			article.addMedia(selectedImagePath);
			System.out.println(article.getMediaPaths().size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void publish() {
		
	}
	
	@FXML
	public void goToProfile() {
		goToScene("profile");
	}
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		author = (UserBean) getBundle().getBean("loggedUser");
		
		article = new ArticleBean();
		categoryPosition = 0;
		typePosition = 0;
		
		categoryChooserLabel.setText(categories.get(categoryPosition));
		typeChooserLabel.setText(types.get(typePosition));
		items = controller.getItemsList();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Image selector");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		
		categories.add("Book");
		categories.add("Movie");
		categories.add("Videogame");
		
		types.add("Review");
		types.add("Guide");
	}
	
	private void loadItems(String category) {
		itemComboBox.getItems().clear();
		for(ItemBean item : items) {
			if(category.charAt(0) == item.getType()) {
				itemComboBox.getItems().add(item);
			}
		}
	}
	
}
