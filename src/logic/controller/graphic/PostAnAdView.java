package logic.controller.graphic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.ItemBean;
import logic.bean.ItemDetailsBean;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.controller.application.ItemAdController;
import logic.controller.application.LoginController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class PostAnAdView extends SceneManageable{

	private ArrayList<ItemBean> items = new ArrayList<>();
	private ArrayList<String> conditions = new ArrayList<>();
	
	private ItemAdController controller = new ItemAdController();
	private LoginController logController = new LoginController();
	
	private FileChooser chooser = new FileChooser();
	
	private UserBean loggedUser;
	private ItemDetailsBean ad = new ItemDetailsBean();
	
	@FXML
	private HBox images;
	@FXML
	private TextField price;
	@FXML
	private TextArea description;
	@FXML
	private ComboBox<String> itemList;
	@FXML
	private ComboBox<String> condition;
	@FXML
	private Button publish;
	@FXML
	private Button addImageButton;
	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		loggedUser = (UserBean) bundle.getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		chooser.setTitle("Image selector");
		chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		
		
		items = (ArrayList<ItemBean>) controller.getItemsList();
		conditions = (ArrayList<String>) controller.getConditionTypes();
		
		for(ItemBean item : items) {
			itemList.getItems().add(item.getItemName());
		}
		
		for(String con : conditions) {
			condition.getItems().add(con);
		}
		
	}
	
	
	
	@FXML
	public void publishAd() {
		int selectedItemIndex = itemList.getSelectionModel().getSelectedIndex();
		ItemBean selectedItem = items.get(selectedItemIndex);
		
		ad.setPrice(Integer.valueOf(price.getText()));
		ad.setDescription(description.getText());
		ad.setCondition(condition.getSelectionModel().getSelectedItem());
		ad.setReferredItemID(selectedItem.getItemID());
		ad.setSeller(loggedUser);

		if(ad.getMedia() == null) {
			ad.setMedia(new ArrayList<>());
		}
		
		controller.post(ad);
		goToScene("sellerpanel");
	}
	
	@FXML
	public void addImage() {
		File selectedImage = chooser.showOpenDialog(null);
		
		if(selectedImage == null)
			return;
		
		String selectedImagePath = selectedImage.getAbsolutePath();
		try {
			ImageView image = new ImageView(new Image(new FileInputStream(selectedImagePath)));
			image.setFitHeight(50.0);
			image.setPreserveRatio(true);
			images.getChildren().add(image);
			ad.addMedia(selectedImagePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
