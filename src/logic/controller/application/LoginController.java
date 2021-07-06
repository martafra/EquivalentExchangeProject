package logic.controller.application;
import logic.bean.LoginBean;

import logic.bean.RegistrationBean;
import logic.bean.UserBean;
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
		newUser.setBirthDate(newUserData.getBirthDate());
		newUser.getWallet().setCurrentCredit(500);
		
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
	
	public void logout(UserBean loggedUser) {
		SessionHandler session = new SessionHandler();
		ConnectionServer serverInstance = ConnectionServer.getInstance();
		String ip = serverInstance.getConnectionData().getIP();
		Integer port = serverInstance.getConnectionData().getPort();
		session.endSession(loggedUser.getUserID(), ip, port);
		serverInstance.stopServer();
		ConnectionServer.resetInstance();
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
		bean.setModerator(loggedUser.isModerator());
		bean.setProfilePicPath(profileData.getProfilePicturePath());
		
		return bean;
	}

	public MailBox connect(UserBean userData){

		var mailBox = new MailBox();
		ConnectionServer server = ConnectionServer.getInstance();
		server.setMailBox(mailBox);
		ConnectionData myServerData = server.getConnectionData();
		SessionHandler session = new SessionHandler();
		
		if(session.startSession(userData.getUserID(), myServerData.getIP() , myServerData.getPort()))
			server.startServer();
			
		return mailBox;
	}
	
	
	
}
