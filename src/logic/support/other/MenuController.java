package logic.support.other;

public abstract class MenuController {
	
	private HeaderController headerBar;
	
	
	public final void attachToHeader(HeaderController hB) {
		headerBar = hB;
		headerBar.setMenuManager(this);
	}
	
	protected void goToScene(String sceneName) {
		switchMenu();
		headerBar.getBodyManager().setScene(sceneName);
	}
	
	protected HeaderController getAttachedHeader() {
		return this.headerBar;
	}
	
	protected void switchMenu() {
		headerBar.getBodyManager().switchMenu();
	}
}
