package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.ElementNotVisibleException;

/**
 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Class :
 * Represents the way Element Not Visible Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_ElementNotVisible extends ElementNotVisibleException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Method
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
	 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Method
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}