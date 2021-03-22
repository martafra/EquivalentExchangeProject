import logic.view.LoginView;

public class Application {

	public static void main(String[] args) {
		
		LoginView view = new LoginView();
		
		view.insertLoginData();
		view.login();
		
		System.out.println("Fine");
	}
}
