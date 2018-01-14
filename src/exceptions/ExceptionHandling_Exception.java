package exceptions;

/**
 * Author Name : Nuttan Abhijan
 * Represents the way Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_Exception extends Exception {

	private String message = "Unknown Error";

	/**
	 * Author Name : Nuttan Abhijan 
	 * : This method handles Exception and returns a customized message
	 */
	public ExceptionHandling_Exception() {
		super();

	}

	/**
	 * Author Name : Nuttan Abhijan 
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}