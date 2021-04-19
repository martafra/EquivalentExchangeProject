package logic.controller.application;
import logic.bean.LoginBean;
import logic.bean.UserBean;
import logic.DAO.UserDAO;
import logic.entity.User;
import logic.support.other.LoginSession;

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
		
		LoginSession.getInstance().setLoginSessionID(userData.getUserID());
		
		return true;
	}
	
	public UserBean getLoggedUser() {
		
		UserBean bean = new UserBean();
		UserDAO userDAO = new UserDAO();
		
		User loggedUser = userDAO.retrieveUser(LoginSession.getInstance().getLoginSessionID());
		
		bean.email = loggedUser.getEmail();
		bean.name = loggedUser.getName();
		bean.lastName = loggedUser.getSurname();
		
		bean.setUserID(loggedUser.getUsername());
		
		return bean;
	}
	
	
	
}
