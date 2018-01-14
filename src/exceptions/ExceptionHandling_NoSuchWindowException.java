package exceptions;

import org.openqa.selenium.NoSuchWindowException;

/**
 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Class :
 * Represents the way No Such Window Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_NoSuchWindowException extends NoSuchWindowException {

	private static String message = "NoSuchWindowException :Unable to switch to the Window as no new Window is present";;

	/**
	 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Method
	 * : This method handles NoSuchWindowException exception and returns a
	 * customized message
	 * 
	 * @throws NoSuchWindowException
	 */
	public ExceptionHandling_NoSuchWindowException() {
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
