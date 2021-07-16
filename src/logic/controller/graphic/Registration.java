package logic.controller.graphic;

import java.time.ZoneId;
import java.util.Date;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.RegistrationBean;
import logic.controller.application.LoginController;
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
	
	LoginController regController = new LoginController();
	
	@FXML
	public void register() {
		
		RegistrationBean rBean = new RegistrationBean();
		
		
		TextField arrayOfNodes[] = {firstName, lastName, username, email, password, confirmPassword};
		for(TextField field : arrayOfNodes) {
			if(field.getText().equals("")) {
				return;
			}
		}
		
		if(birthDate.getValue() == null) {
			return;
		}
		
		if(!password.getText().equals(confirmPassword.getText())) {
			return;
		}
		
		if(!rBean.validateNames(firstName.getText())) {
			return;
		}
		else {
			rBean.setName(firstName.getText());
		}
		
		if(!rBean.validateNames(lastName.getText())) {
			return;
		}
		else {
			rBean.setLastName(lastName.getText());
		}
		
		if(!rBean.validateUsername(username.getText())) {
			return;
		}
		else {
			rBean.setUsername(username.getText());
		}
		
		
		if(!rBean.validateEmail(email.getText())) {
			return;
		}
		else {
			rBean.setEmail(email.getText());
		}
		
		if(!rBean.validatePassword(password.getText())) {
			return;
		}
		else {
			rBean.setPassword(password.getText());
		}
		
		Date date = Date.from(birthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		if(!rBean.validateBirthDate(date)) {
			return;
		}
		else {
			rBean.setBirthDate(date);
		}
		
		regController.register(rBean);
		
		
	}
	


}
