package logic.entity;
import java.util.Date;

import logic.enumeration.Gender;

public class User {

	private String username;
	private String name;
	private String surname;
	private Gender gender;
	private Date birthDate;
	private String email;
	private String password;
	private Wallet wallet;
	
	public User(String username, String name, String surname, String email, String password, Integer credit) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.wallet = new Wallet();
		wallet.setCurrentCredit(credit);
	}
	public User(String username, String name, String surname,String gender, Date birthDate, String email, String password, Integer credit) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		setGender(gender);
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.wallet = new Wallet();
		wallet.setCurrentCredit(credit);
	}
	
	
	public User() {
		
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getName() {
		return this.name;
	}
	public String getSurname() {
		return this.surname;
	}
	public Gender getGender() {
		return this.gender;
	}
	public Date getBirthDate() {
		return this.birthDate;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPassword() {
		return this.password;
	}
	public void setUsername(String user) {
		this.username = user;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String sur) {
		this.surname = sur;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public void setGender(String gender) {
		if(gender != null) {
			for (Gender value : Gender.values()) {
				if (gender.equals(value.toString().substring(0, 1))){
					  this.gender = value;
					  return;
				}
			}
		}
		this.gender = null;
	}
	public void setBirthDate(Date date) {
		this.birthDate = date;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String passwd) {
		this.password = passwd;
	}
	public boolean increaseCredit(Integer n) {
		if (n < 0) {
			return false;
		}
		
		else {
			Integer credit = wallet.getCurrentCredit();
			wallet.setCurrentCredit( (credit+n) );
			return true;
		}	
	}
	public boolean decreaseCredit(Integer n) {
		if (n < 0) {
			return false;
		}
		
		Integer credit = wallet.getCurrentCredit();
		if ( ( credit - n) >= 0 ) {
			wallet.setCurrentCredit( (credit-n) );
			return true;}
		
		else {return false;}
		
	}
	
	public Wallet getWallet() {
		return this.wallet;
	}
}
