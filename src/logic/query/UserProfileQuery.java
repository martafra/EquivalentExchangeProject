package logic.query;

public class UserProfileQuery extends Query{
	public String selectProfileDataByUsername(String username) {
		username = quote(username);
		String query = "SELECT proPic, coverPic, bioInfo, phoneNumber FROM User WHERE username = %s;";
		return String.format(query, username);
	}
	
	public String selectProfilePictureByUsername(String username) {
		username = quote(username);
		String query = "SELECT proPic FROM User WHERE username = %s;";
		return String.format(query, username);
	}
	
	public String updateProfile(String username, String proPicPath, String coverPic, String bio, String phoneNumber) {
		username = quote(username);
		proPicPath = quote(proPicPath);
		proPicPath = proPicPath.replace('\\', '/');
		coverPic = quote(coverPic);
		coverPic = coverPic.replace('\\', '/');
		bio = quote(bio);
		phoneNumber = quote(phoneNumber);
		
		String query = "UPDATE User SET "
					 + "proPic = LOAD_FILE(%s), "
					 + "coverPic = LOAD_FILE(%s), "
					 + "bioInfo = %s, "
					 + "phoneNumber = %s "
					 + "WHERE username = %s;";
		
		return String.format(query, proPicPath, coverPic, bio, phoneNumber, username);
	}
	
}
