package logic.support.other;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import logic.support.interfaces.SceneManageable;

public class PaneManager extends StackPane{
	
	private HashMap<String, Node> loadedScenes = new HashMap();
	private HashMap<String, SceneManageable> sceneControllers = new HashMap();

	private Node getScene(String name){
        return this.loadedScenes.get(name);
    }

	private void addScene(String name, Node scene){
        // TODO aggiungere eventuali controlli
        this.loadedScenes.put(name, scene);
    }

    private boolean removeScene(String name){

        if (this.loadedScenes.get(name) != null){
            
            this.loadedScenes.remove(name);
            return true;
        }
        return false;
    }
    
    public void loadScene(String name, String resourcePath) {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
    	try {
			Parent newScene = (Parent) loader.load();
			SceneManageable sceneController = (SceneManageable) loader.getController();
			sceneController.setPaneManager(this);
			
			sceneControllers.put(name, sceneController);
			
			this.addScene(name, newScene);
		} catch (IOException e) {
			// TODO Generare una scena di errore che sostituisca quella mancante
			System.out.println("Error in file: " + resourcePath);
		}	
    }
    
    public void setScene(String name) {
    	
    	if(!getChildren().isEmpty()) {
    		getChildren().remove(0);
    	}
    	
    	getChildren().add(0, getScene(name));  
    	sceneControllers.get(name).onLoad();
    }

    
	
}
