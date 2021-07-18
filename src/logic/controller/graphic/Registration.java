package logic.controller.graphic;

import java.time.ZoneId;
import java.util.Date;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.RegistrationBean;
import logic.controller.application.LoginController;
import logic.support.exception.AlreadyRegisteredUserException;
import logic.support.other.SceneManageable;

public class Registration extends SceneManageable{
	
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
	private Label errorLabel;
	
	LoginController regController = new LoginController();
	
	private Boolean checkIfEmpty() {
		TextField[] arrayOfNodes = {firstName, lastName, username, email, password, confirmPassword};
		for(TextField field : arrayOfNodes) {
			if(field.getText().equals("")) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void onExit() {
		errorLabel.setText("");
	}
	
	private Boolean checkAndInsert(RegistrationBean rBean) {
		
		
		if(Boolean.TRUE.equals(checkIfEmpty())) {
			errorLabel.setText("Insert all fields");
			return false;
		}
		
		
		if(birthDate.getValue() == null) {
			errorLabel.setText("Insert a birth date");
			return false;
		}
		
		if(!password.getText().equals(confirmPassword.getText())) {
			errorLabel.setText("Password fields must be the same");
			return false;
		}
		
		if(Boolean.FALSE.equals(rBean.validateNames(firstName.getText()))) {
			errorLabel.setText("Insert a correct name");
			return false;
		}
		else {
			rBean.setName(firstName.getText());
		}
		
		if(Boolean.FALSE.equals(rBean.validateNames(lastName.getText()))) {
			errorLabel.setText("Insert a correct last name");
			return false;
		}
		else {
			
			rBean.setLastName(lastName.getText());
		}
		
		if(Boolean.FALSE.equals(rBean.validateUsername(username.getText()))) {
			errorLabel.setText("Insert a correct username");
			return false;
		}
		else {
			rBean.setUsername(username.getText());
		}
		
		
		if(Boolean.FALSE.equals(rBean.validateEmail(email.getText()))) {
			errorLabel.setText("Insert a correct email");
			return false;
		}
		else {
			rBean.setEmail(email.getText());
		}
		if(Boolean.FALSE.equals(rBean.validatePassword(password.getText()))) {
			errorLabel.setText("Insert a correct password [8-16 chars] [containing numbers, letters, and special characters]");
			return false;
		}
		else {
			rBean.setPassword(password.getText());
		}
		
		Date date = Date.from(birthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		if(Boolean.FALSE.equals(rBean.validateBirthDate(date))) {
			errorLabel.setText("You are too young to join EquivalentExchange");
			return false;
		}
		else {
			rBean.setBirthDate(date);
		}
		return true;
		
	}
	
	@FXML
	public void register() {
		
		RegistrationBean rBean = new RegistrationBean();
		
		if(Boolean.FALSE.equals(checkAndInsert(rBean))){
			return;
		}
		
		try {
			regController.register(rBean);
			goToScene("home");
		} catch (AlreadyRegisteredUserException e) {
			switch(e.getCode()) {
				case 2:
					errorLabel.setText("User already Registered");
					break;
				case 1:
					errorLabel.setText("Email already in use");
					break;
				case 0:
				default:
					break;
			}
		}
		
		
	}
	


}
