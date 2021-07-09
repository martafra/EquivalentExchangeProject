package logic.bean;

import logic.support.interfaces.Bean;

public class LoginBean implements Bean{

	protected String userID;
	protected String password;
	
	public void setUserID(String id) {
		this.userID = id;
	}
	public String getUserID() {
		return userID;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
		
}
