package exceptions;

import org.openqa.selenium.UnhandledAlertException;

/**
 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Class :
 * Represents the way Unhandled Alert Exception is handled
 */
@SuppressWarnings("serial")
class ExceptionHandling_UnhandledAlertException extends UnhandledAlertException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Method
	 * : This method handles UnhandledAlertException exception and returns a
	 * customized message
	 * 
	 * @throws UnhandledAlertException
	 */
	public ExceptionHandling_UnhandledAlertException() {
		super(message);
		message = "Unhandled Alert has popped up";

	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Method
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
