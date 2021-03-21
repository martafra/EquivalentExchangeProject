package logic.view;
import logic.bean.LoginBean;
import java.util.Scanner;
import logic.controller.LoginController;

public class LoginView {

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
