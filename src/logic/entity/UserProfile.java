package logic.entity;

public class UserProfile {
	
	private String profilePicturePath;
	private String coverPicturePath;
	private String bioInfo;
	private String phoneNumber;
	
	public String getProfilePicturePath() {
		return profilePicturePath;
	}
	
	public String getCoverPicturePath() {
		return coverPicturePath;
	}
	
	public String getBioInfo() {
		return this.bioInfo;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	} 
	
	public void setProfilePicturePath(String profilePicture) {
		this.profilePicturePath = profilePicture;
	}
	
	public void setCoverPicturePath(String coverPicture) {
		this.coverPicturePath = coverPicture;
	}
	
	public void setBioInfo(String bio) {
		this.bioInfo = bio;
	}
	
	public void setPhoneNumber(String num) {
		this.phoneNumber = num;
	}
	
}
