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
	
}
