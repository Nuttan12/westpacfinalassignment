package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.TimeoutException;

/**
 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Class :
 * Represents the way Timeout Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_TimeoutException extends TimeoutException {

	private String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 16-09-2016 Purpose of Method
	 * : This method handles TimeoutException exception and returns a customized
	 * message
	 * 
	 * @throws TimeoutException
	 */
	public ExceptionHandling_TimeoutException(String controlName) {

		super("");
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlName);

		message = " TimeoutException :Unable to find the Element :" + obj.getControlName()
				+ "  with in the Time Limit of 60 sec";

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