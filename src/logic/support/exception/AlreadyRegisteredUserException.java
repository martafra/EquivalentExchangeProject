package logic.support.exception;

public class AlreadyRegisteredUserException extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Integer code;
	
	/*
	 * code
	 * 	0 = OK
	 *  1 = email already in use
	 *  2 = username already in use
	 * 
	 * 
	 */
	
	public AlreadyRegisteredUserException(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
	
}
