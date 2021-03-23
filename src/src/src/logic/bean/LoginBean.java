package logic.bean;

public class LoginBean {

	private String userID;
	private String passwd;
	
	public void setUserID(String id) {
		this.userID = id;
	}
	public String getUserID() {
		return userID;
	}
	
	public void setPassword(String passwd) {
		this.passwd = passwd;
	}
	public String getPassword() {
		return passwd;
	}
		
}
