package logic.DAO;
import logic.entity.User;

public class UserDAO {

	
	private String retUsername = "Wibbley712";
	private String retName = "Marco";
	private String retSurname = "Valerio";
	private String retEmail = "marcovalerio@outlook.it";
	private String retPasswd = "Brusio";
	
	public User retrieveUser(String username) {
		
		User user = new User();
		//query the db for a suser that has "username" as username
		if(retUsername.equals(username)) {
			user.setUsername(retUsername);
			user.setName(retName);
			user.setSurname(retSurname);
			user.setEmail(retEmail);
			user.setPassword(retPasswd);
		}
		else {
			user = null;
		}
		
		return user;
	}
	
}
