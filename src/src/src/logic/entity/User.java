package logic.entity;

public class User {

	private String username;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Wallet wallet;
	
	public String getUsername() {
		return this.username;
	}
	public String getName() {
		return this.name;
	}
	public String getSurname() {
		return this.surname;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPassword() {
		return this.password;
	}
	public Wallet getWallet() {
		return this.wallet;
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
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String passwd) {
		this.password = passwd;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
}
