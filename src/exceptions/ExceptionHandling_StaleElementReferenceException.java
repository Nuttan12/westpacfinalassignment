package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.StaleElementReferenceException;

/**
 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Class :
 * Represents the way Stale Element Reference Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_StaleElementReferenceException extends StaleElementReferenceException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Method
	 * : This method handles StaleElementReferenceException exception and
	 * returns a customized message
	 * 
	 * @throws StaleElementReferenceException
	 */
	public ExceptionHandling_StaleElementReferenceException(String controlname) {
		super(null);
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlname);

		message = " StaleElementReferenceException :Element with ControlProperty :" + obj.getControlProperty()
				+ " and TypeofProperty :" + obj.getTypeOfProperty()
				+ " is currently Stale[the element no longer appears on the DOM of the page.]";

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