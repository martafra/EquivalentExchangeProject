package logic.bean;

import java.time.LocalDate;
import java.time.Period;

import logic.support.interfaces.Bean;
import logic.support.other.ProfileRules;

public class RegistrationBean implements Bean{
	
	private String username;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private LocalDate birthDate;
	
	
	public String getUsername() {
		return this.username;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}
	
	public boolean setUsername(String username) {
		
		username = username.toLowerCase();
		
		if(!username.matches("[a-z0-9]*"))
			return false;
		
		this.username = username;
		return true;
	}
	
	public boolean setPassword(String password) {
		
		boolean presenceFlag = false;
		
		if(password.length() < ProfileRules.getMinPasswordLenght() &&
			password.length() > ProfileRules.getMaxPasswordLenght()) {
			return false;
		}
		
		for(Character disabledChar : ProfileRules.getPassNotAllowed()) {
			if(password.contains(disabledChar.toString())) {
				return false;
			}
		}
		
		for(String requiredSet : ProfileRules.getPassRequiredCharactersSet()) {
			for(int i = 0; i < requiredSet.length(); i++) {
				if(password.contains(Character.toString(requiredSet.charAt(i)))) {
					presenceFlag = true;
					break;
				}
			}
			if(!presenceFlag)
				return false;
			presenceFlag = false;			
		}
		
		this.password = password;
		return true;	
	}
	
	public boolean setName(String name) {
		
		if(!isAlphabetic(name))
			return false;
		this.name = name;
		return true;

	}
	
	public boolean setLastName(String lastName) {

		if(!isAlphabetic(lastName))
			return false;
		this.lastName = lastName;
		return true;
	}
	
	

	public boolean setEmail(String email){

		email = email.toLowerCase();
	
		if(!email.matches("[0-9a-z]+[\\.[0-9a-z]+]*@[0-9a-z]+[\\.[0-9a-z]+]*"))
			return false;
		
		this.email = email;
		return true;
	
	}
	
	public boolean setBirthDate(LocalDate birthDate) {
		int minimumAge = ProfileRules.getMinimumAge();
		LocalDate todayDate = LocalDate.now();
		
		int userAge = Period.between(birthDate, todayDate).getYears();
		
		if(userAge < minimumAge) {
			return false;
		}
		
		this.birthDate = birthDate;
		return true;
	}
	
	private boolean isAlphabetic(String word) {
		return word.matches("[a-zA-Z ]*");
	}
	
	
}
