package logic.query;


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

	// private String attObligatori = clUsername + "," + clFirstName + "," +
	// clLastName + "," + clEmail + "," + clPassword;

	public String selectUser(String username) {
		return "SELECT * FROM " + table + " WHERE " + clUsername + "='" + username + "'";
	}

	public String selectUser(String username, String password) {
		return "SELECT * FROM " + table + " WHERE " + clUsername + "='" + username + "'" + " AND " + clPassword + "='"
				+ password + "'";
	}

	public String checkUser(String username) {
		return "SELECT username FROM user WHERE username = '" + username + "'";
	}
	

	public String insertUser(String username, String firstName, String lastName, String email, String password) {
		return insertStr + 
						"(" + clUsername + ", " + clFirstName + ", " + clLastName + ", " + clEmail + ", "
								+ clPassword + ") "
						+ "VALUES ('" + username + "','" + firstName + "','" + lastName + "','" + email + "','"
								+ password + "')";
	}

	public String updateGender(String username, char gender) {
		return updateStr + clGender + " = '" + gender + "' WHERE " + clUsername + "= '" + username + "'";
	}

	public String updateBirthDate(String username, String birthDate) {
		return updateStr + clBirthdate + "=  '" + birthDate + "' WHERE " + clUsername + "= '" + username + "'";
	}

	public String updateCredit(String username, int newCredit) {
		return updateStr + clCredit + " = " + newCredit + " WHERE " + clUsername + " = '" + username + "'";
	}
}
