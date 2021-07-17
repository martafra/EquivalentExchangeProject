package logic.controller.graphic;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.Parent;

public class GraphicWidget {

	private HashMap<String, Node> internalComponents = new HashMap<>();
	
	protected void setComponent(String componentID, Node node) {
		internalComponents.put(componentID, node);
	}
	
	protected Node getComponent(String componentID) {
		return internalComponents.get(componentID);
	}
	
	protected void loadComponents(Parent parentNode) {
		
		if(parentNode == null)
			return;
		
		for(Node node : parentNode.getChildrenUnmodifiable()) {
			
			if(node.getId() != null)
			{
				setComponent(node.getId(), node);
			}
			
			try {
				if(!((Parent) node).getChildrenUnmodifiable().isEmpty())
					loadComponents((Parent) node);
			}
			catch(ClassCastException e){
				//Do nothing if you can't cast
			}
		}
	}
	
}
