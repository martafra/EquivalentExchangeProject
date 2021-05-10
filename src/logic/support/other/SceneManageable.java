package logic.support.other;

public abstract class SceneManageable {
	
	protected PaneManager sceneManager;
	protected Bundle bundle;
	
	public void setPaneManager(PaneManager manager) {
		sceneManager = manager;
	}
	
	public void onLoad(Bundle bundle) {
		this.bundle = bundle;
	}
	
	public final Bundle getBundle() {
		return bundle;
	}
	
	protected final void resetBundle() {
		bundle = new Bundle();
	}
	
	
	
}
