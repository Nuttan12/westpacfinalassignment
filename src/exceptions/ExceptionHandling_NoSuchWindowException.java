package exceptions;

import org.openqa.selenium.NoSuchWindowException;

/**
 * Author Name : Nuttan Abhijan
 * Represents the way No Such Window Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_NoSuchWindowException extends NoSuchWindowException {

	private static String message = "NoSuchWindowException :Unable to switch to the Window as no new Window is present";;

	/**
	 * Author Name : Nuttan Abhijan
	 * : This method handles NoSuchWindowException exception and returns a
	 * customized message
	 * 
	 * @throws NoSuchWindowException
	 */
	public ExceptionHandling_NoSuchWindowException() {
		super(message);

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
