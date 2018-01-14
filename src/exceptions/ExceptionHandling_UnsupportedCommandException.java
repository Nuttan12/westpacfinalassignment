package exceptions;

import org.openqa.selenium.UnsupportedCommandException;

/**
 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Class :
 * Represents the way Unsupported Command Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_UnsupportedCommandException extends UnsupportedCommandException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Method
	 * : This method handles UnsupportedCommandException exception and returns a
	 * customized message
	 * 
	 * @throws UnsupportedCommandException
	 */
	public ExceptionHandling_UnsupportedCommandException() {
		super(message);
		message = "UnsupportedCommandException:Command used by the webdriver is unsupported.";

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