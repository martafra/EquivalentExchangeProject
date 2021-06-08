package logic.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.entity.UserProfile;
import logic.query.UserProfileQuery;
import logic.support.database.MyConnection;
import logic.support.other.ImageCache;

public class UserProfileDAO {

	MyConnection connection = MyConnection.getInstance();
	UserProfileQuery profileQuery = new UserProfileQuery();
	
	public UserProfile selectProfileByUsername(String username, Boolean onlyProPic) {
		
		UserProfile profileData = new UserProfile();
		Statement stmt = null;
		ResultSet rs = null;
	
		Connection con = connection.getConnection();
		try {
			stmt = con.createStatement();
			String query = null;
			
			if(Boolean.TRUE.equals(onlyProPic))
				query = profileQuery.selectProfilePictureByUsername(username);
			else
				query = profileQuery.selectProfileDataByUsername(username);
			
			rs = stmt.executeQuery(query);
			
			if(!rs.next()) {
				return null;
			}
			
			ImageCache mediaCache = ImageCache.getInstance();
			String fileName = username + "_profilePic";
			String filePath = mediaCache.addImage(fileName, rs.getBinaryStream("proPic"));
			profileData.setProfilePicturePath(filePath);
			
			if(Boolean.FALSE.equals(onlyProPic)) {
				
				
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return profileData;
		
	}
	
	
}
