package logic.bean;

public class UserProfileBean extends UserBean{
	
	private String gender;
	private Integer age;
	private String coverPicPath;
	private String city;
	private String country;
	private String description;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getCoverPicPath() {
		return coverPicPath;
	}
	public void setCoverPicPath(String coverPicPath) {
		this.coverPicPath = coverPicPath;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return this.description;
	}
	
	
}
