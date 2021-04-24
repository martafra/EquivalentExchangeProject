package logic.support.other;

public class ProfileRules {

	private final static Integer minPasswordLenght = 8;
	private final static Integer maxPasswordLenght = 20;
	
	private final static Character[] passwordNotAllowedCharacters = {' ', '\t', '\n', '/', '\\', '<', '>', '='};
	private final static String[] passwordRequiredCharactersSet = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
													  			   "abcdefghijklmnopqrstuvwxyz",
													  			   "0123456789",
													  			   ".@?,!#"};
	
	private final static Integer minimumAge = 14;
	
	public static Integer getMinPasswordLenght() {
		return minPasswordLenght;
	}
	
	public static Integer getMaxPasswordLenght() {
		return maxPasswordLenght;
	}
	
	public static Character[] getPassNotAllowed() {
		return passwordNotAllowedCharacters;
	}
	
	public static String[] getPassRequiredCharactersSet() {
		return passwordRequiredCharactersSet;
	}	
	
	public static int getMinimumAge() {
		return minimumAge;
	}
}
