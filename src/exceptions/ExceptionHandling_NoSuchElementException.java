package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.NoSuchElementException;

/**
 * Author Name : Nuttan Abhijan
 * Represents the way No Such Element Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_NoSuchElementException extends NoSuchElementException {

	private String message = null;

	/**
	 * Author Name : Nuttan Abhijan
	 * : This method handles NoSuchElementException exception and returns a
	 * customized message
	 * 
	 * @throws NoSuchElementException
	 */
	public ExceptionHandling_NoSuchElementException(String controlname) {

		super(null);
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlname);

		message = " NoSuchElementException :Element with ControlName :" + obj.getControlName()
				+ "  is not present on the current page";

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
