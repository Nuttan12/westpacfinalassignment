package exceptions;

/**
 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Class :
 * Represents the way Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_Exception extends Exception {

	private String message = "Unknown Error";

	/**
	 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Method
	 * : This method handles Exception and returns a customized message
	 */
	public ExceptionHandling_Exception() {
		super();

	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Method
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}