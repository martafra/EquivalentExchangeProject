package logic.query;

import logic.entity.User;

public class UserQuery {
	private String table = "user";

	private String updateStr = "UPDATE " + table + " SET ";
	//private String selectAll = "SELECT * from " + table;
	private String insertStr = "INSERT INTO " + table;

	private String clUsername = "username";
	private String clPassword = "passwd";
	private String clFirstName = "firstName";
	private String clLastName = "lastName";
	private String clEmail = "email";
	private String clGender = "gender";
	private String clBirthdate = "birthDate";
	private String clCredit = "credit";
	private String columnsName = " (" + clUsername + ", "   + clPassword + ", " + clFirstName + ", "  + clLastName+ ", " +
			 clEmail+ ", " +   clGender + ", " +   clBirthdate + ", " +  clCredit + ") ";


	public String selectUser(String username) {
		return "SELECT * FROM " + table + " WHERE " + clUsername + "='" + username + "'";
	}

	public String selectUser(String username, String password) {
		return "SELECT * FROM " + table + " WHERE " + clUsername + "='" + username + "'" + " AND " + clPassword + "='"
				+ password + "'";
	}

	public String changeStr(String str) {
		if(str !=null) {
			return "'" + str + "'";
		}
		return str;
	}
	
	public String insertUser(User user) {
		String query = insertStr;
		String username = changeStr(user.getUsername());
		String password = changeStr(user.getPassword());
		String firstName = changeStr(user.getName());
		String lastName =changeStr(user.getSurname());
		String email = changeStr(user.getEmail());
		
		String gender = null;
		if(user.getGender()!=null) {
			gender = "'" + user.getGender().toString().charAt(0) + "'";
		}
		
		String birthdate = null; 
		if(user.getBirthDate()!=null) {
			birthdate = changeStr(user.getBirthDate().toString());
		}
		
		int credit= user.getWallet().getCurrentCredit();

		String str= String.format(query + columnsName + " VALUES (%s,%s,%s,%s,%s,%s,%s,%d)", username, password, firstName, lastName, email,gender, birthdate, credit);
		return str;
	}

	public String updateUser(User user) {
		String query = updateStr;
		String username = changeStr(user.getUsername());
		String password = changeStr(user.getPassword());
		String firstName = changeStr(user.getName());
		String lastName =changeStr(user.getSurname());
		String email = changeStr(user.getEmail());
		
		String gender = null;
		if(user.getGender()!=null) {
			gender = "'" + user.getGender().toString().charAt(0) + "'";
		}
		
		String birthdate = null; 
		if(user.getBirthDate()!=null) {
			birthdate = changeStr(user.getBirthDate().toString());
		}
		
		int credit= user.getWallet().getCurrentCredit();

		String str= String.format(query + " %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %s, %s = %d WHERE %s = %s", 
				clUsername, username, clPassword, password, clFirstName, firstName, clLastName, lastName, clEmail, email,
				clGender, gender, clBirthdate, birthdate, clCredit, credit, clUsername, username );
		return str;
	}
	
	public String deleteUser(String username) {
		return "DELETE FROM " + table + " WHERE " + clUsername +" = '" + username + "'";
	}
}
