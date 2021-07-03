package logic.query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserQuery extends Query{

	public String selectModerators(Boolean isModerator) {
		
		Integer isMod = 0;
		if(Boolean.TRUE.equals(isModerator))
			isMod = 1;
		
		String query = "SELECT * FROM User WHERE isModerator = %d";
		return String.format(query, isMod);
	}
	
	public String selectUser(String username) {
		username = quote(username);
		String query = "SELECT * FROM User WHERE username = %s;";
		return String.format(query, username);
	}
	
	public String insertUser(String username, String password, String name, String lastName, String email, String gender, Date birthDate, Integer credit) {
		
		DateFormat format = new SimpleDateFormat(dateFormat);
		String birthDateString = format.format(birthDate);

		username = quote(username);
		password = quote(password);
		name = quote(name);
		lastName = quote(lastName);
		email = quote(email);
		gender = quote(gender);
		birthDateString = quote(birthDateString);
		
		String query = "INSERT INTO User (username, firstName, lastName, email, passwd, gender, birthDate, credit) "+
					   "VALUES (%s, %s, %s, %s, %s, %s, %s, %d);";
		
		return String.format(query, username, name, lastName, email, password, gender, birthDateString, credit);
	}

	public String updateUser(String username, String password, String name, String lastName, String email, String gender, Date birthDate, Integer credit) {
		
		DateFormat format = new SimpleDateFormat(dateFormat);
		
		String birthDateString = "NULL";
		
		username = quote(username);
		password = quote(password);
		name = quote(name);
		lastName = quote(lastName);
		email = quote(email);
		
		if(gender != "NULL")
			gender = quote(gender);
		if(birthDate != null)
		{
			birthDateString = format.format(birthDate);
			birthDateString = quote(birthDateString);
		}

		String query = "update User SET " +
					   "passwd = %s," +
					   "firstName = %s," +
					   "lastName = %s," +
					   "email = %s," +
					   "gender = %s," +
					   "birthDate = %s," +
					   "credit = %d " +
					   "WHERE username = %s";
					   
		return String.format(query, password, name, lastName, email, gender, birthDateString, credit, username);
	}
	
	public String deleteUser(String username) {
		String query = "DELETE FROM user WHERE username = %s;";
		return String.format(query, username);
	}
}
