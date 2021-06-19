package logic.controller.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import logic.DAO.UserDAO;
import logic.DAO.UserProfileDAO;
import logic.bean.ItemInSaleBean;
import logic.bean.UserBean;
import logic.bean.UserProfileBean;
import logic.entity.User;
import logic.entity.UserProfile;

public class ProfileController {

	private Random random = new Random(); //TODO Per regola di sonarcloud
	
	public UserProfileBean getUserProfileData(UserBean userData) {
		
		UserProfileBean userProfileData = new UserProfileBean();
		String username = userData.getUserID();
		UserProfileDAO profileDAO = new UserProfileDAO();
		UserDAO userDAO = new UserDAO();
		UserProfile profileData = profileDAO.selectProfileByUsername(username, false);
		User user = userDAO.selectUser(username);
		
		userProfileData.setUserID(user.getUsername());
		userProfileData.setName(user.getName());
		userProfileData.setLastName(user.getSurname());
		userProfileData.setEmail(user.getEmail());
		
		String gender;
		if(user.getGender() == null)
			gender = "null";
		else {
			gender = user.getGender().toString();
		}
		
		userProfileData.setGender(gender);
		userProfileData.setProfilePicPath(profileData.getProfilePicturePath());
		userProfileData.setCoverPicPath(profileData.getProfilePicturePath());
		userProfileData.setDescription(profileData.getBioInfo());
		userProfileData.setAge(calculateAge(user.getBirthDate()));
		
		return userProfileData;
	}
	
	private Integer calculateAge(Date birthDate) {
		Calendar calendar = new GregorianCalendar();
		Date now = new Date();
		calendar.setTime(now);
		Integer todayYear = calendar.get(Calendar.YEAR);
		Integer todayMonth = calendar.get(Calendar.MONTH);
		Integer todayDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(birthDate);
		Integer birthYear = calendar.get(Calendar.YEAR);
		Integer birthMonth = calendar.get(Calendar.MONTH);
		Integer birthDay = calendar.get(Calendar.DAY_OF_MONTH);
		
				
		Integer age = todayYear - birthYear;
		
		if(todayMonth < birthMonth) 
			age--;
		else if(todayMonth.equals(birthMonth) && todayDay < birthDay)
			age = age - 1;
		return age;
	}
	
	public void updateUserProfileData(UserProfileBean profileDataBean) {
		String username = profileDataBean.getUserID();
		UserProfileDAO profileDAO = new UserProfileDAO();
		UserDAO userDAO = new UserDAO();
		UserProfile profileData = profileDAO.selectProfileByUsername(username, false);
		User user = userDAO.selectUser(username);
		
		if(profileDataBean.getProfilePicPath() != null) {
			profileData.setProfilePicturePath(profileDataBean.getProfilePicPath());
		}
		
		if(profileDataBean.getCoverPicPath() != null) {
			profileData.setCoverPicturePath(profileDataBean.getCoverPicPath());
		}
		
		if(profileDataBean.getDescription() != null) {
			profileData.setBioInfo(profileDataBean.getDescription());
		}
		
		userDAO.updateUser(user);
		profileDAO.updateProfile(username, profileData);
	}
	
	public List<ItemInSaleBean> getProductsByUser(UserBean user, Integer numberOfProducts) {
		
		
		SellController sellController = new SellController();
		List<ItemInSaleBean> products = sellController.getItemList(user);
		List<ItemInSaleBean> filteredProducts = new ArrayList<>();
		
		if(numberOfProducts >= products.size()) {
			return products;
		}	
		
		for(Integer i = 0; i < numberOfProducts; i++) {
			int position = random.nextInt(products.size());
			if(products.get(position) != null)
				filteredProducts.add(products.remove(position));
		}
		return filteredProducts;
	}
	
}
