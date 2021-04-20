package logic.enumeration;

public enum Gender {
	FEMALE, MALE, NON_BINARY, OTHER;
	
	public static Gender getGender(String gender) {
		if (gender.equals('F')){
			return Gender.FEMALE;
		}
		if (gender.equals('M')){
			return Gender.MALE;
		}
		if (gender.equals('N')){
			return Gender.NON_BINARY;
		}
		return Gender.OTHER;
	}
}
