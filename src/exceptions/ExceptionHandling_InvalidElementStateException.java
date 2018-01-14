package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.InvalidElementStateException;

/**
 * Author Name : Nuttan Abhijan
 * Represents the way Invalid Element State Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_InvalidElementStateException extends InvalidElementStateException {

	private static String message = null;

	/**
	 * Author Name : Nuttan Abhijan
	 * : This method handles InvalidElementStateException exception and returns
	 * a customized message
	 * 
	 * @throws InvalidElementStateException
	 */
	public ExceptionHandling_InvalidElementStateException(String controlname) {
		super(message);
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlname);

		message = " InvalidElementStateException:Action on Element with ControlProperty :" + obj.getControlProperty()
				+ " and TypeofProperty :" + obj.getTypeOfProperty() + " is not possible";

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