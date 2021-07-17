package logic.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import logic.support.interfaces.Bean;
import logic.support.other.ProfileRules;

public class RegistrationBean implements Bean{
	
	private String username;
	private String name;
	private String lastName;
	private String email;
	private String password;
	private Date birthDate;
	
	
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

	public Date getBirthDate() {
		return this.birthDate;
	}
	
	public void setUsername(String username) {
		
		username = username.toLowerCase();
		this.username = username;
	}
	
	public void setPassword(String password) {
		
		this.password = password;	
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmail(String email){
		email = email.toLowerCase();
		this.email = email;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public Boolean validateUsername(String username) {

		return !username.matches("[a-z0-9]*");
			
	}
	
	public Boolean validatePassword(String password) {
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
		
		return true;
	}
	
	public Boolean validateNames(String name) {
		return !isAlphabetic(name);
			
	}
	
	public Boolean validateEmail(String email) {
		return !email.matches("[0-9a-z]+[\\.[0-9a-z]+]*@[0-9a-z]+[\\.[0-9a-z]+]*");
			
	}
	
	public Boolean validateBirthDate(Date birthDate) {
		int minimumAge = ProfileRules.getMinimumAge();
		
		int userAge = checkAge(birthDate);
		
		return (userAge >= minimumAge);
			
		
	}
	
	private Integer checkAge(Date date) {
		Calendar cal = new GregorianCalendar();
		Date today = new Date();
		cal.setTime(today);
		Integer todayYear = cal.get(Calendar.YEAR);
		Integer todayMonth = cal.get(Calendar.MONTH);
		Integer todayDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(date);
		Integer birthYear = cal.get(Calendar.YEAR);
		Integer birthMonth = cal.get(Calendar.MONTH);
		Integer birthDay = cal.get(Calendar.DAY_OF_MONTH);	
		Integer age = todayYear - birthYear;
		if(todayMonth < birthMonth || (todayMonth.equals(birthMonth) && todayDay < birthDay)) 
			age--;
		return age;
	}
	
	private boolean isAlphabetic(String word) {
		return word.matches("[a-zA-Z ]*");
	}
	
	
}
