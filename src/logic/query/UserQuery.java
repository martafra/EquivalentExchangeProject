package logic.query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserQuery extends Query{

	public String selectUser(String username) {
		username = quote(username);
		String query = "SELECT * FROM User WHERE username = %s;";
		return String.format(query, username);
	}
	
	public String insertUser(String username, String password, String name, String lastName, String email, String gender, Date birthDate, Integer credit) {
		
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		String birthDateString = format.format(birthDate);

		username = quote(username);
		password = quote(password);
		name = quote(name);
		lastName = quote(lastName);
		email = quote(email);
		gender = quote(gender);
		birthDateString = quote(birthDateString);
		gender = quote(gender);
		
		String query = "INSERT INTO User (username, firstName, lastName, email, passwd, gender, birthDate, credit) "+
					   "VALUES (%s, %s, %s, %s, %s, %s, %s, %d);";
		
		return String.format(query, username, name, lastName, email, password, gender, birthDateString, credit);
	}

	public String updateUser(String username, String password, String name, String lastName, String email, String gender, Date birthDate, Integer credit) {
		
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		String birthDateString = format.format(birthDate);

		username = quote(username);
		password = quote(password);
		name = quote(name);
		lastName = quote(lastName);
		email = quote(email);
		gender = quote(gender);
		birthDateString = quote(birthDateString);
		gender = quote(gender);

		String query = "update User SET " +
					   "passwd = %s," +
					   "firstName = %s," +
					   "lastName = %s," +
					   "email = %s," +
					   "gender = %s," +
					  "birthDate = %s," +
					   "credit = %d" +
					   " WHERE username = %s";
					   
		return String.format(query, password, name, lastName, email, gender, birthDateString, credit, username);
	}
	
	public String deleteUser(String username) {
		String query = "DELETE FROM user WHERE username = %s;";
		return String.format(query, username);
	}
}
