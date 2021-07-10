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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.ItemBean;
import logic.bean.ItemDetailsBean;
import logic.bean.UserBean;
import logic.controller.application.ItemAdController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class PostAnAdView extends SceneManageable implements Initializable{

	private List<ItemBean> items = new ArrayList<>();
	private List<String> conditions = new ArrayList<>();
	private List<String> types = new ArrayList<>();
	private ArrayList<ItemCase> itemCases = new ArrayList<>();
	private ItemCase chosenItemCase = null;
	private ItemAdController controller = new ItemAdController();	
	private FileChooser chooser = new FileChooser();
	private UserBean loggedUser;
	private ItemDetailsBean ad = new ItemDetailsBean();
	private String selectedType = " ";
	private static final String BOOK_TEXT = "Book";
	private static final String MOVIE_TEXT = "Movie";
	private static final String VIDEOGAME_TEXT = "Videogame";
	
	@FXML
	private HBox images;
	@FXML
	private TextField price;
	@FXML
	private TextField address;
	@FXML
	private TextArea description;
	@FXML
	private ComboBox<String> itemTypeList;
	@FXML
	private ComboBox<String> condition;
	@FXML
	private Button publish;
	@FXML
	private Button addImageButton;
	@FXML
	private VBox itemList;
	@FXML
	private TextField itemFilterField;
	@FXML
	private Pane itemSelector;
	
	@FXML
	public void onItemTypeSelected() {
		if (itemTypeList.getSelectionModel().getSelectedItem() == null) {
			
			return;
			
		}
		if(!itemTypeList.getSelectionModel().getSelectedItem().equals(selectedType)) {
			selectedType = itemTypeList.getSelectionModel().getSelectedItem();
			filterItems(selectedType);
			itemSelector.setVisible(true);
		}
	}	

	
	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		loggedUser = (UserBean) bundle.getBean("loggedUser");
		
		if(loggedUser == null) {
			goToScene("login");
			return;
		}
		
		itemSelector.setVisible(false);
		
		chooser.setTitle("Image selector");
		chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		itemFilterField.textProperty().addListener((observable, oldValue, newValue) -> 
			filterItems(selectedType)
		);

		conditions = controller.getConditionTypes();
		condition.getItems().addAll(conditions);
		
	}
	
	@Override 
	public void onExit() {
		items = new ArrayList<>();
		//itemCases = new ArrayList<>();
		ad = new ItemDetailsBean();
		selectedType = " ";
		images.getChildren().clear();
		price.setText("");
		address.setText("");
		description.setText("");
		//itemList.getChildren().clear();
		condition.getSelectionModel().clearSelection();
		itemTypeList.getSelectionModel().clearSelection();
		itemFilterField.setText("");
		chosenItemCase = null;
	}
	
	private void filterItems(String type) {
		String filterText = itemFilterField.getText();
		
		Integer position = 0;
		for(ItemCase item : itemCases) {
			if(type.charAt(0) == item.getData().getType() && item.getData().getItemName().contains(filterText))
			{
				item.color(position % 2 == 0);
				position++;
				item.setVisible(true);
			}
			else {
				item.setVisible(false);
			}
			
		}
		
	}
	
	
	@FXML
	public void publishAd() {
		ItemBean selectedItem = chosenItemCase.getData();
		
		ad.setPrice(Integer.valueOf(price.getText()));
		ad.setAddress(address.getText());
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
			image.setFitHeight(100.0);
			image.setPreserveRatio(true);
			image.setOnMouseClicked((MouseEvent e) -> {
				images.getChildren().remove(image);
				ad.removeMedia(selectedImagePath);
			});
			
			images.getChildren().add(image);
			ad.addMedia(selectedImagePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new Thread() {
			@Override
			public void run() {
					
					types.add(BOOK_TEXT);
					types.add(MOVIE_TEXT);
					types.add(VIDEOGAME_TEXT);
					
					items = controller.getItemsList();
					itemTypeList.getItems().addAll(types);
					Integer position = 0;
					for(ItemBean item : items) {
						ItemCase itemCase = new ItemCase(item);
						itemCases.add(itemCase);
						itemCase.color(position % 2 == 0);
						itemCase.getPane().setOnMouseClicked((MouseEvent e) -> {
							chosenItemCase = itemCase;
							itemFilterField.setText(chosenItemCase.getData().getItemName());
						});
						itemList.getChildren().add(itemCase.getPane());
						position++;
					}
			}
		}.start();
		
	}
	
}
