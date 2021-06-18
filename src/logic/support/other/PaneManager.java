package logic.support.other;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import logic.controller.graphic.DefaultGraphicController;

public class PaneManager extends StackPane{
	
	private HashMap<String, Node> loadedScenes = new HashMap<>();
	private HashMap<Node, SceneManageable> sceneControllers = new HashMap<>();
	private HeaderController headerBarController = null;
	private Node headerContent = null;
	private Node menuContent = null;
	private Boolean menuState = false;
	
	private static final Integer PREFERRED_WIDTH = 1280;
	private static final Integer PREFERRED_HEIGHT = 720;
	
	public PaneManager() {
		super();
		setSize();
	}
	
	public void loadHeaderBar(String resourcePath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
		try {
			this.headerContent = loader.load();
			bindHeaderBarController((HeaderController) loader.getController());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void bindHeaderBarController(HeaderController headerBar) {
		
		headerBar.setBodyManager(this);
		this.headerBarController = headerBar;
		headerBar.onLoad();
	}
	
	public void loadMenu(String resourcePath) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
		try {
			this.menuContent = loader.load();
			MenuController menuController = loader.getController();
			menuController.attachToHeader(headerBarController);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void switchMenu() {
		
		
		if(!getChildren().isEmpty()) {
			
			if(!menuState) {
				getChildren().add(1, menuContent);
			}else {
				getChildren().remove(1);
			}
			
			menuState = !menuState;
			
		}
	}
	

	private Node getScene(String name){
        return this.loadedScenes.get(name);
    }

	private void addScene(String name, Node scene){
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
    	
    	Parent newScene = null;
    	try {
    		
			newScene = (Parent) loader.load();
			
			SceneManageable sceneController = (SceneManageable) loader.getController();
			
			sceneController.setPaneManager(this);
			sceneControllers.put(newScene, sceneController);
			
			this.addScene(name, newScene);
		} catch (IOException e) {
			System.out.println("Error in file: " + resourcePath);
		} catch (NullPointerException e) {
			SceneManageable defaultController = new DefaultGraphicController();
			defaultController.setPaneManager(this);
			sceneControllers.put(newScene, defaultController);
			this.addScene(name, newScene);
			
		}
    }
    
    public void setScene(String name) {
    	
    	Bundle bundle = new Bundle();
    	
    	if(!getChildren().isEmpty()) {
    		sceneControllers.get(getChildren().get(0)).onExit();
    		bundle = sceneControllers.get(getChildren().get(0)).getBundle();
    		getChildren().remove(0);
    	}
    	
    	getChildren().add(0, getScene(name));  
    	sceneControllers.get(getScene(name)).onLoad(bundle);
    	
    	headerUpdate();
    }
    
    public SceneManageable getCurrentSceneController() {
    	return sceneControllers.get(getChildren().get(0));
    }

	private void setSize() {
		this.setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}

	public Node getHeaderContent() {
		return this.headerContent;
	}
	
	public void headerUpdate() {
		headerBarController.updateHeader();
	}

	public HeaderController getHeaderController() {
		return this.headerBarController;
	}

    
	
}
