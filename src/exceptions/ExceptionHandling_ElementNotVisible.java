package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.ElementNotVisibleException;

/**
 * Author Name : Nuttan Abhijan
 * Represents the way Element Not Visible Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_ElementNotVisible extends ElementNotVisibleException {

	private static String message = null;

	/**
	 * Author Name : Nuttan Abhijan
	 * : This method handles ElementNotVisibleException exception and returns a
	 * customized message
	 * 
	 * @throws ElementNotVisibleException
	 */
	public ExceptionHandling_ElementNotVisible(String controlName) {

		super(null);
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlName);

		message = " ElementNotVisibleException:Element with ControlProperty :" + obj.getControlProperty()
				+ " and TypeofProperty :" + obj.getTypeOfProperty() + " is not visible on the current page";

	}

	/**
	 * Author Name :Nuttan Abhijan Swain
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}