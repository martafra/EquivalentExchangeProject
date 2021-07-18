package logic.support.exception;
/*
 * @author Marta Fraioli
 * */
public class WrongLoginCredentialsException extends Exception {

	private static final long serialVersionUID = 1L;
	private final Integer code;
	/*
	 * code
	 * 	0 = OK
	 *  1 = username doesn't exist
	 *  2 = incorrect password
	 * 
	 * 
	 */
	public WrongLoginCredentialsException(Integer code) {
		this.code = code;
	}
	public Integer getCode() {
		return this.code;
	}
	
}
