package logic.support.exception;

public class InsufficientCreditException extends Exception  {
	
	private static final long serialVersionUID = 1L;

	public InsufficientCreditException(String errorMessage) {
        super(errorMessage);
    }
}
