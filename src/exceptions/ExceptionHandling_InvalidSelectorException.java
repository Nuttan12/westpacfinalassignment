package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.InvalidSelectorException;

/**
 * Author Name : Hema Sai Date of Preparation : 14-09-2016 Purpose of Class :
 * Represents the way Invalid Selector Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_InvalidSelectorException extends InvalidSelectorException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 14-09-2016 Purpose of Method
	 * : This method handles InvalidSelectorException exception and returns a
	 * customized message
	 * 
	 * @throws InvalidSelectorException
	 */
	public ExceptionHandling_InvalidSelectorException(String controlname) {

		super(null);
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlname);

		message = "InvalidSelectorException:Unable to find Element with given ControlProperty :"
				+ obj.getControlProperty() + " and TypeofProperty :" + obj.getTypeOfProperty() + " is not possible";

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