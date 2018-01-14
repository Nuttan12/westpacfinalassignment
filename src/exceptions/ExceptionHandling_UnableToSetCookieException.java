package exceptions;

import org.openqa.selenium.UnableToSetCookieException;

/**
 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Class :
 * Represents the way Unable ToSet Cookie Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_UnableToSetCookieException extends UnableToSetCookieException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Method
	 * : This method handles UnableToSetCookieException exception and returns a
	 * customized message
	 * 
	 * @throws UnableToSetCookieException
	 */
	public ExceptionHandling_UnableToSetCookieException() {
		super(message);
		message = " UnableToSetCookieException :Unable to set the Cookie";

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