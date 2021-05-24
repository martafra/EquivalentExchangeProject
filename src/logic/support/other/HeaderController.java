package logic.support.other;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public abstract class HeaderController{
	
	private PaneManager bodyManager;
	protected Parent headerBarContent;
	
	public final void setBodyManager(PaneManager panel) {
		this.bodyManager = panel;
	}
	
	protected final PaneManager getBodyManager() {
		return this.bodyManager;
	}
	
	protected final void goToScene(String sceneID) {
		bodyManager.setScene(sceneID);
	}
	
	public abstract void updateHeader();
	
	public abstract void onLoad();
	
	public Parent getContent() {
		return headerBarContent;
	}
	
}
