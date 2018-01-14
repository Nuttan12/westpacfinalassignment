package exceptions;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.openqa.selenium.NotFoundException;

/**
 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Class :
 * Represents the way Not Found Exception is handled
 */
@SuppressWarnings("serial")
public class ExceptionHandling_NotFoundException extends NotFoundException {

	private static String message = null;

	/**
	 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Method
	 * : This method handles NotFoundException exception and returns a
	 * customized message
	 * 
	 * @throws NotFoundException
	 */
	public ExceptionHandling_NotFoundException(String controlname) {

		super(message);
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlname);
		message = " NotFoundException :Unable to find the Element for given ControlProperty :"
				+ obj.getControlProperty() + " and TypeofProperty :" + obj.getTypeOfProperty();

	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 15-09-2016 Purpose of Method
	 * : Returns the customized message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}