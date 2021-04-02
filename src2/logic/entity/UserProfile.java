package logic.entity;

public class UserProfile {
	
	/*private String profilePicture;
	private String coverPicture;
	private accounts;
	private address*/
	private String bioInfo;
	private String phoneNumber;
	
	public String getBioInfo() {
		return this.bioInfo;
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	} 
	
	public void setBioInfo(String bio) {
		this.bioInfo = bio;
	}
	public void setPhoneNumber(String num) {
		this.phoneNumber = num;
	}
	
}
