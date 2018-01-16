package utilities;

import java.util.List;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;


import exceptions.ExceptionHandling_NoSuchElementException;

/**
 * Author Name : Nuttan Abhijan 
 * Represents the functions which are used to handle various actions performed
 * on the Application under Test
 */
public class WebElementFactory {

	protected RemoteWebDriver driver;
	protected WebElementFactory elementFactory;
	protected ScreenShots screenshots;

	/**
	 * Author Name : Nuttan Abhijan
	 * : Constructor declaration
	 */
	public WebElementFactory(RemoteWebDriver driver) {
		this.driver = driver;
		screenshots = new ScreenShots(driver);
	}

	/**
	 * Author Name : Nuttan Abhijan
	 * : Performs operations clear, click, click and hold, double click, context
	 * click, moves to specific element, releases mouse control
	 */
	public void doMouseAction(String controlName, String typeOfAction) {
		WebElement element = getElement(controlName);
		Actions actions = new Actions(driver);
		Action action;
		switch (typeOfAction) {
		case "clear":
			element.clear();
			break;
		case "click":
			element.click();
			break;
		case "clickandhold":
			actions.clickAndHold(element);
			action = actions.build();
			action.perform();
			break;
		case "hover":
			actions.moveToElement(element);
			action = actions.build();
			action.perform();
			break;
		default:
			break;
		}
	}
	/**
	 * Author Name : Nuttan Abhijan
	 * : Returns the text of the web element
	 */
	public String getElementText(String controlName) {
		WebElement element = getElement(controlName);
		String Text = element.getText();
		return Text;
	}

	/**
	 * Author Name : Nuttan Abhijan
	 * : Gets the locator for identifying the ui elements uniquely
	 */
	public By getLocator(String controlProperty, String propertyType) {

		switch (propertyType) {
		case "xpath":
			return By.xpath(controlProperty);
		case "id":
			return By.id(controlProperty);
		case "name":
			return By.name(controlProperty);
		case "classname":
			return By.className(controlProperty);
		default:
			return null;
		}
	}

	/**
	 * Author Name : Nuttan Abhijan
	 * : Uses the control name to retrieve and identify the element uniquely
	 */
	public WebElement getElement(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlName);
		By elementLocator = getLocator(obj.getControlProperty(), obj.getTypeOfProperty());
		List<WebElement> element = driver.findElements(elementLocator);
		if (element.size() == 0) {
			screenshots.takeScreenShots();
			Reporter.log("Element not present in page");
			throw new ExceptionHandling_NoSuchElementException(controlName);
		} else {
			return element.get(0);
		}
	}

	/**
	 * Author Name : Nuttan Abhijan
	 * Waits for the specific element to be displayed on the page for the
	 * specified time interval
	 * 
	 * @throws NoSuchElementException
	 *             NullPointerException StaleElementReferenceException
	 */
	public void dynamicWait(String controlName, long maxSeconds) {
		ObjectFactory factory = new ObjectFactory();
		By elementLocator = null;
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		try {
			UIControlObject obj = map.get(controlName);
			elementLocator = getLocator(obj.getControlProperty(), obj.getTypeOfProperty());
		} catch (NullPointerException | NoSuchElementException exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
		long maxMilliseconds = maxSeconds * 10;
		long additionalDelay = 2;
		long elapsedMilliseconds = 100;
		while (++elapsedMilliseconds < maxMilliseconds) {
			try {
				WebElement element = driver.findElement(elementLocator);
				element.isDisplayed();
				break;
			} catch (NullPointerException | NoSuchElementException | StaleElementReferenceException exception) {
				Reporter.log(ExceptionUtils.getStackTrace(exception));
			}
			try {
				Thread.sleep(elapsedMilliseconds + additionalDelay);
			} catch (Exception exception) {
				Reporter.log(ExceptionUtils.getStackTrace(exception));
			}
			elapsedMilliseconds = elapsedMilliseconds + additionalDelay;
		}
	}
}