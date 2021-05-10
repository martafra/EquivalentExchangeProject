package logic.support.other;

public class ProfileRules {

	private static final Integer MIN_PASSWORD_LENGHT = 8;
	private static final Integer MAX_PASSWORD_LENGHT = 20;
	
	private static final Character[] passwordNotAllowedCharacters = {' ', '\t', '\n', '/', '\\', '<', '>', '='};
	private static final String[] passwordRequiredCharactersSet = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
													  			   "abcdefghijklmnopqrstuvwxyz",
													  			   "0123456789",
													  			   ".@?,!#"};

	private static final Integer MIN_AGE = 14;
	
	private ProfileRules() {}
	
	public static Integer getMinPasswordLenght() {
		return MIN_PASSWORD_LENGHT;
	}
	
	public static Integer getMaxPasswordLenght() {
		return MAX_PASSWORD_LENGHT;
	}
	
	public static Character[] getPassNotAllowed() {
		return passwordNotAllowedCharacters;
	}
	
	public static String[] getPassRequiredCharactersSet() {
		return passwordRequiredCharactersSet;
	}	
	
	public static int getMinimumAge() {
		return MIN_AGE;
	}
}
