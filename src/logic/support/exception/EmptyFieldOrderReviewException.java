package logic.support.exception;

public class EmptyFieldOrderReviewException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private final Integer code;
	/**
	 * code
	 *  0 = OK
	 *  1 = empty field
	 */
	public EmptyFieldOrderReviewException(Integer code) {
		this.code = code;
	}
	public Integer getCode() {
		return this.code;
	}

}
