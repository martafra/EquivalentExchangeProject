package logic.controller.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.RegistrationBean;
import logic.controller.application.LoginController;
import logic.support.other.Bundle;
import logic.support.other.PaneManager;
import logic.support.other.SceneManageable;

public class Registration extends SceneManageable{

	PaneManager myManager = new PaneManager();
	
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField username;
	@FXML
	private TextField email;
	@FXML
	private DatePicker birthDate;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField confirmPassword;
	@FXML
	private Button register;
	
	@FXML
	public void register() {
		
		RegistrationBean rBean = new RegistrationBean();
		LoginController regController = new LoginController();
		
		TextField arrayOfNodes[] = {firstName, lastName, username, email, password, confirmPassword};
		for(TextField field : arrayOfNodes) {
			if(field.getText().equals("")) {
				System.out.println("Manca il campo: " + field.getId());
				return;
			}
		}
		
		if(birthDate.getValue() == null) {
			System.out.println("Manca il campo: Data");
			return;
		}
		
		if(!password.getText().equals(confirmPassword.getText())) {
			System.out.println("Le password inserite non coincidono");
			return;
		}
		
		if(!rBean.setName(firstName.getText())) {
			System.out.println("Errore nell'inserimento del nome");
			return;
		}
		
		if(!rBean.setLastName(lastName.getText())) {
			System.out.println("Errore nell'inserimento del cognome");
			return;
		}
		
		if(!rBean.setUsername(username.getText())) {
			System.out.println("Errore nell'inserimento dell'username");
			return;
		}
		
		if(!rBean.setEmail(email.getText())) {
			System.out.println("Errore nell'inserimento della email");
			return;
		}
		
		if(!rBean.setPassword(password.getText())) {
			System.out.println("Errore nell'inserimento della Password");
			return;
		}
		
		if(!rBean.setBirthDate(birthDate.getValue())) {
			System.out.println("Errore nell'inserimento della Data");
			return;
		}
		
		regController.register(rBean);
		
		
	}
	


}
