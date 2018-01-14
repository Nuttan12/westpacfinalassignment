package exceptions;

import org.openqa.selenium.SessionNotCreatedException;

/**
 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Class :
 * Represents the way Session Not Created Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_SessionNotCreatedException extends SessionNotCreatedException {

	private static String message = "Unable to Create Session";

	/**
	 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Method
	 * : This method handles SessionNotCreatedException exception and returns a
	 * customized message
	 * 
	 * @throws SessionNotCreatedException
	 */
	public ExceptionHandling_SessionNotCreatedException() {
		super(message);

	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Method
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}