package logic.support.other;

public class LoginSession {
	
	private String username;
	private Integer sessionTime;
	private static LoginSession instance = null;
	
	private LoginSession() {}
	
	public static LoginSession getInstance() {
		
		if(instance == null) {
			instance = new LoginSession();
		}
		return instance;
		
	}
	
	public void setLoginSessionID(String username) {
		this.username = username;
	}
	
	public String getLoginSessionID() {
		return this.username;
	}
	
	public void logoutFromSession() {
		this.instance = null;
	}

}
