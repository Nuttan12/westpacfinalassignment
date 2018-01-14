package exceptions;

import org.openqa.selenium.NoAlertPresentException;

/**
 * Author Name : Hema Sai Date of Preparation : 14-09-2016 Purpose of Class :
 * Represents the way No Alert Present Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_NoAlertPresentException extends NoAlertPresentException {

	private static String message = "No Alert or pop-up is displayed";

	/**
	 * Author Name : Hema Sai Date of Preparation : 14-09-2016 Purpose of Method
	 * : This method handles NoAlertPresentException exception and returns a
	 * customized message
	 * 
	 * @throws NoAlertPresentException
	 */
	public ExceptionHandling_NoAlertPresentException() {
		super();
	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 14-09-2016 Purpose of Method
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
