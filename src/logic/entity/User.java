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
	public void setBirthDate(Date date) {
		this.birthDate = date;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String passwd) {
		this.password = passwd;
	}
}
