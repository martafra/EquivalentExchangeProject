package logic.bean;

import logic.support.interfaces.Bean;

public class UserBean implements Bean{
	
	private String username;
	private String name;
	private String lastName;
	private String email;
	private String profilePicPath;
	
	public String getUserID() {
		return this.username;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getProfilePicPath() {
		return this.profilePicPath;
	}
	
	public void setUserID(String username) {
		this.username = username;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setProfilePicPath(String picPath) {
		this.profilePicPath = picPath;
	}
	
}
