package logic.controller.graphic;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import logic.bean.ItemInSaleBean;
import logic.controller.application.CatalogueController;
import logic.support.other.Bundle;
import logic.support.other.SceneManageable;

public class CatalogueView extends SceneManageable {
	
	private CatalogueController controller = new CatalogueController();
	private List<ItemInSaleBean> itemInSaleBeanList;
	private ArrayList<String> genres = new ArrayList<>();
	private boolean inizialize = false;
	
	private String searchStr;
	private String typeStr;
	private String genreStr;
	private String consoleStr;
	
	
	@FXML
	private VBox vbox;
	@FXML
	private ListView<ItemInSaleBean> list;
	@FXML
	private TextField searchBar;
	@FXML
	private Button searchBtn;
	@FXML
	private RadioButton book;
	@FXML
	private RadioButton movie;
	@FXML
	private RadioButton videogame;
	@FXML
	private RadioButton all;
	@FXML
	private ComboBox<String> genre;
	@FXML 
	private ToggleGroup type;
	
	


	@Override
	public void onLoad(Bundle bundle) {
		super.onLoad(bundle);
		
		itemInSaleBeanList = controller.getListItemInSaleBean();
		
		genre.setVisible(false);
		all.setSelected(true);
		
		if (searchBar.getText()!=null) {
			searchBar.setText("");
		}
		
		setListView();	
	}
	
	static class Cell extends ListCell<ItemInSaleBean>{
		
		//Creo il loader che mi carica la mia interfaccia per il singolo oggetto
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/logic/view/itemCatalogue.fxml"));
		
		//Vbox dove mettere l'interfaccia per singolo oggetto
		private VBox box;
		
		public Cell() {
			try {
				//Metto l'interfaccia nel Vbox;
				box = loader.load();
					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
			
		//chiamato per inserire i valori degli item nell'intefaccia creata da cell
		@Override
		public void updateItem(ItemInSaleBean item, boolean empty) {
			super.updateItem(item, empty);
		
			//imposto a nulli i valori base della lista, cio che mostra come testo e interfaccia
			setText(null);
			setGraphic(null);
			
			if(item != null && !empty) {  //Controllo se c'e' l'elemento
				
				//prendo il controller grafico dell'interfaccia ItemCatalogue
				ItemCatalogueView controller = loader.getController();
				
				//Chiamo il metodo per settare i valori dell'interfaccia
				controller.setView(item);
				
				//Imposto l'interfaccia come elemento da mostrare nella riga della Lost
				setGraphic(box);
			}
		}
	}
	
	public void setListView() {
		
		// Passo la lista di bean a questo metodo per renderla una lista di oggetti osservabili
		ObservableList<ItemInSaleBean> data = FXCollections.observableArrayList(itemInSaleBeanList);
		
		
		// Passo la lista osservabile alla mia lista di JavaFX
		list.setItems(data);
		if (!inizialize) {
			// Aggiungo la Cella che si occupa di visualizzare la schermata dell'oggetto
			list.setCellFactory(param -> new Cell());
			
			list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemInSaleBean>() {

				@Override
				public void changed(ObservableValue<? extends ItemInSaleBean> observable, ItemInSaleBean oldValue, ItemInSaleBean newValue) {
					
					if (newValue == null) {
						return;
					}
					Bundle bundle = getBundle();
					bundle.addBean("selectedItem", newValue);
					goToScene("itemDetails");
				}
			});
			inizialize = true;
		}
	}
	
	public void search(Event e){
    	searchStr = searchBar.getText();
    	doSearch();
    }
	
	public void doSearch() {
		String[] filters = {searchStr, typeStr, genreStr, consoleStr};
		if( (filters[0] != null && !filters[0].isBlank() ) || filters[1]!= null) {
			itemInSaleBeanList = controller.getListItemInSaleBeanFiltered(filters);
		}else {
			itemInSaleBeanList = controller.getListItemInSaleBean();
		}
		ObservableList<ItemInSaleBean> data = FXCollections.observableArrayList(itemInSaleBeanList);
		list.setItems(data);
		
	}
	
	public void all(){
		typeStr = null;
		genre.setVisible(false);
		genreStr = null;
		consoleStr = null;
		doSearch();
		
    }
	
	public void book(){
		typeStr = "B";
		setGenreList(typeStr);
		doSearch();
		
    }
	
	public void movie(){
		typeStr = "M";
		setGenreList(typeStr);
		doSearch();
	}	
	
	public void videogame(){
		typeStr = "V";
		setGenreList(typeStr);
		doSearch();
		
    }
	
	public void genre() {
		genreStr = genre.getValue();
		doSearch();
	}
	
	public void setGenreList(String type) { //TODO far riapparire la scritta GENRE
		
		genre.getItems().clear();
		genre.setVisible(true);
		genres = (ArrayList<String>) controller.getGenre(type.charAt(0));
		
		for(String gen : genres) {
			genre.getItems().add(gen);
		}
	
	}
	
	
}
