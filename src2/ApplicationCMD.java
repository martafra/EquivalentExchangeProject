import logic.controller.graphic.LoginViewController;

public class ApplicationCMD {

	public static void main(String[] args) {
		
		LoginViewController view = new LoginViewController();
		
		view.insertLoginData();
		view.login();
		
		System.out.println("Fine");
	}
}
