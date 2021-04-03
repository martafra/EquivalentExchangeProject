package logic.controller.application;
import logic.bean.LoginBean;
import logic.DAO.UserDAO;
import logic.entity.User;

public class LoginController {

	public Boolean login(LoginBean userData) {
		
		UserDAO userDB = new UserDAO();
		User loggedUser = userDB.retrieveUser(userData.getUserID());
		
		if(loggedUser == null) {
			return false;
		}
		
		if(!(loggedUser.getPassword().equals(userData.getPassword()))) {
			return false;
		}
		
		return true;
	}
	
	
	
}
