package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.NoSuchFrameException;

/**
 * Author Name : Nuttan Abhijan
 * Represents the way No Such Frame Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_NoSuchFrameException extends NoSuchFrameException {

	private static String message = null;

	/**
	 * Author Name : Nuttan Abhijan
	 * : This method handles NoSuchFrameException exception and returns a
	 * customized message
	 * 
	 * @throws NoSuchFrameException
	 */
	public ExceptionHandling_NoSuchFrameException(String controlName) {

		super(message);
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlName);

		message = " NoSuchFrameException :Unable to switch to the Frame as no Frame is present  with FrameName:"
				+ obj.getControlName();

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