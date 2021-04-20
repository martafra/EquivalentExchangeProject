package logic.database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.entity.User;
import logic.enumeration.Gender;



public class queryUser {

	MyDB myDB;
	
	public queryUser() {
		myDB = new MyDB();
		myDB.connect();	
	}
	
	public void insertUser(String username, String firstName, String lastName, String email, String password, char gender, String birthdate, String proPic, String coverPic, String bioInfo, String phoneNumber, String homeAddress, boolean isModerator, int credit  ) {
		myDB.doUpdate("INSERT INTO User values('"+ username + "','" + firstName + "','" + lastName + "','" + email + "','" + password + "','" + gender + "','" + birthdate + "','" + proPic+ "','" + coverPic + "','" + bioInfo + "','" + phoneNumber + "','" + homeAddress + "'," + isModerator+ "," + credit +")"); 	
	}
	
	public void insertUser(String username, String firstName, String lastName, String email, String password) {
		myDB.doUpdate("INSERT INTO User values('"+ username + "','" + firstName + "','" + lastName + "','" + email + "','"+ password +"', null, null, null, null, null, null, null, null, null)");
	}
	
	
	public void updateGender(String username,char gender) { 
		myDB.doUpdate("UPDATE User SET gender = '"+ gender + "' WHERE username = '" + username + "'");
	}
	
	public void updateBirthDate(String username,String birthDate) {
		myDB.doUpdate("UPDATE User SET birthDate =  '"+ birthDate + "' WHERE username = '" + username + "'");
	}
	
	public void updateProPic(String username,String proPic) {
		myDB.doUpdate("UPDATE User SET proPic = '"+ proPic + "' WHERE username = '" + username + "'");
	}
	
	public void updateCoverPic(String username,String coverPic) {
		myDB.doUpdate("UPDATE User SET coverPic = '"+ coverPic + "' WHERE username = '" + username + "'");
	}
	
	public void updatePhoneNumber(String username,String phoneNumber) {
		myDB.doUpdate("UPDATE User SET phoneNumber = '"+ phoneNumber + "' WHERE username = '" + username + "'");
	}
	
	public void updateHomeAddress(String username,String homeAddress) {
		myDB.doUpdate("UPDATE User SET homeAddress = '"+ homeAddress + "' WHERE username = '" + username + "'");
	}
	
	public void updateIsModerator(String username,boolean isModerator) {
		myDB.doUpdate("UPDATE User SET isModerator = "+ isModerator + " WHERE username = '" + username + "'");
	}
	

	
	
	public User selectUser(String username, String password) throws SQLException {
		ResultSet rs = myDB.makeQuery("SELECT * FROM User WHERE username ='" + username + "' AND passwd ='" + password + "'");
		rs.next();
		Date date = Date.valueOf(rs.getString(7));
		Gender gender = Gender.getGender(rs.getString(6));
		User user = new User(rs.getString(1),rs.getString(2),rs.getString(3),gender,date,rs.getString(4),rs.getString(5));
		 return user;
		//myDB.showAll(rs);	
	}
	
	public User selectUser(String username) throws SQLException {
		ResultSet rs = myDB.makeQuery("SELECT * FROM User WHERE username ='" + username + "'");
		rs.next();
		Date date = Date.valueOf(rs.getString(7));
		Gender gender = Gender.getGender(rs.getString(6));
		User user = new User(rs.getString(1),rs.getString(2),rs.getString(3),gender,date,rs.getString(4),rs.getString(5));
		return user;
		//myDB.showAll(rs);	
	}
	
	public int getCredit(String username) throws SQLException {
		ResultSet rs = myDB.makeQuery("SELECT credit FROM User WHERE username ='" + username + "'");
		rs.next();
		return rs.getInt(1);
		
		//myDB.showAll(rs);	
	}
	
	public void updateCredit(String username, int newCredit) {
		myDB.doUpdate("UPDATE User SET credit = "+ newCredit  + " WHERE username = '" + username + "'");
		
	}
	
	
	
	

}
