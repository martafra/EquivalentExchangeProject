package logic.controller.application;
import logic.bean.LoginBean;
import logic.bean.RegistrationBean;
import logic.bean.UserBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import logic.DAO.UserDAO;
import logic.DAO.UserProfileDAO;
import logic.entity.User;
import logic.entity.UserProfile;
import logic.support.connection.ConnectionData;
import logic.support.connection.ConnectionServer;
import logic.support.connection.SessionHandler;
import logic.support.other.MailBox;


public class LoginController {
	
	public Boolean register(RegistrationBean newUserData) {
		
		//check if exists another user with the same username or email
		
		if(false)
			return false;
		
		UserDAO userDB = new UserDAO();
		User newUser = new User();
		newUser.setUsername(newUserData.getUsername());
		newUser.setName(newUserData.getName());
		newUser.setSurname(newUserData.getLastName());
		newUser.setEmail(newUserData.getEmail());
		newUser.setPassword(newUserData.getPassword());
		
		//userDB.registerUser(newUser);
		userDB.insertUser(newUser);
		
		return true;
		
	}

	public Boolean login(LoginBean userData) {
		
		UserDAO userDB = new UserDAO();
		User loggedUser = userDB.selectUser(userData.getUserID());
		
		if(loggedUser == null) {
			return false;
		}

		if(!loggedUser.getPassword().equals(userData.getPassword())){
			return false;
		}
		return true;
	}
	
	public void logout() {
		SessionHandler session = new SessionHandler();
		session.endSession(null, null, 0);
	}
	
	
	public UserBean getUserByLoginData(LoginBean loginData) {
		
		//TODO Considerare se cambiare il parametro in un tipo LoginBean
		var bean = new UserBean();
		UserDAO userDAO = new UserDAO();
		UserProfileDAO profileDAO = new UserProfileDAO();
		
		User loggedUser = userDAO.selectUser(loginData.getUserID());
		UserProfile profileData = profileDAO.selectProfileByUsername(loginData.getUserID(), true);
		
		
		bean.setEmail(loggedUser.getEmail());
		bean.setName(loggedUser.getName());
		bean.setLastName(loggedUser.getSurname());
		bean.setUserID(loggedUser.getUsername());
		bean.setProfilePicPath(profileData.getProfilePicturePath());
		
		return bean;
	}

	public MailBox connect(UserBean userData){

		var mailBox = new MailBox();
		ConnectionData myServerData = new ConnectionServer(mailBox).getConnectionData();
		SessionHandler session = new SessionHandler();
		
		if(session.startSession(userData.getUserID(), myServerData.getIP() , myServerData.getPort()))
			return mailBox;
		return null;
	}
	
	
	
}
