package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.InvalidElementStateException;

/**
 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Class :
 * Represents the way Invalid Element State Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_InvalidElementStateException extends InvalidElementStateException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Method
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
	 * Author Name : Hema Sai Date of Preparation : 13-09-2016 Purpose of Method
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}