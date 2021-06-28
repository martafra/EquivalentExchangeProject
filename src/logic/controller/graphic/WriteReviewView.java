package logic.controller.graphic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.ArticleBean;
import logic.bean.ItemBean;
import logic.controller.application.WriteReviewController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class WriteReviewView extends SceneManageable implements Initializable{

	private ArrayList<String> texts = new ArrayList<>();
	private Integer selectedPanel = 0;
	private FileChooser fileChooser;
	private ArticleBean article = new ArticleBean();
	private WriteReviewController controller = new WriteReviewController();
	private ArrayList<String> categories = new ArrayList<>();
	private Integer categoryPosition = 0;
	private ArrayList<String> types = new ArrayList<>();
	private Integer typePosition = 0;
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
	private ComboBox<String> itemComboBox;
	
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
	public void viewPreview() {
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
			
			imageBox.getChildren().add(image);
			article.addMedia(selectedImagePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
	
	private void loadItems(String string) {
		itemComboBox.getItems().clear();
		for(ItemBean item : items) {
			if(categories.get(categoryPosition).charAt(0) == item.getType()) {
				itemComboBox.getItems().add(item.getItemName());
			}
		}
	}
	
}
