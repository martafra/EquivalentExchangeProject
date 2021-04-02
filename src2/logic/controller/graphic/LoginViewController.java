package logic.controller.graphic;
import logic.bean.LoginBean;
import logic.controller.application.LoginController;

import java.util.Scanner;

public class LoginViewController {

	private LoginBean bean = new LoginBean();
	private LoginController log = new LoginController();
	
	
	public void insertLoginData() {
		
		Scanner scn = new Scanner(System.in);
		
		System.out.println("Inserire Username: ");
		
		bean.setUserID(scn.nextLine());
		
		System.out.println("Inserire Password: ");
		
		bean.setPassword(scn.nextLine());
		
		scn.close();
	}
	
	public void login() {
		
		Boolean ret = log.login(bean);
		
		System.out.println("Utente Loggato? " + ret);
	
		
	}
	
	
	
	
	
}
