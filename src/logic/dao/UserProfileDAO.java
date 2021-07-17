package logic.dao;

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
			if(filePath.equals(mediaCache.getMissingImagePath()))
				filePath = "/logic/view/assets/images/avatar.png";
			
			profileData.setProfilePicturePath(filePath);
			
			if(Boolean.FALSE.equals(onlyProPic)) {
				
				fileName = username + "_coverPic";
				filePath = mediaCache.addImage(fileName, rs.getBinaryStream("coverPic"));
				if(filePath.equals(mediaCache.getMissingImagePath()))
					filePath = "/logic/view/assets/images/avatar.png";
				
				profileData.setCoverPicturePath(filePath);
				profileData.setPhoneNumber(rs.getString("phoneNumber"));
				profileData.setBioInfo(rs.getString("bioInfo"));
				
			}	
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null)
					stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return profileData;
		
	}

	public void updateProfile(String username, UserProfile profileData) {
		Statement stmt = null;
		Connection con = connection.getConnection();
		try {
			stmt = con.createStatement();
			String coverPicPath = profileData.getCoverPicturePath();
			String profilePicPath = profileData.getProfilePicturePath();
			String phoneNumber = profileData.getPhoneNumber();
			String bio = profileData.getBioInfo();
			
			String query = profileQuery.updateProfile(username, profilePicPath, coverPicPath, bio, phoneNumber);
			stmt.executeUpdate(query);	
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
