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
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import exceptions.ExceptionHandling_ElementNotVisible;
import exceptions.ExceptionHandling_NoSuchElementException;

/**
 * Author Name : Hema Sai Date of Preparation : 11-09-2016 Purpose of Class :
 * Represents the functions which are used to handle various actions performed
 * on the Application under Test
 */
public class WebElementFactory {

	protected RemoteWebDriver driver;
	protected WebElementFactory elementFactory;
	protected ScreenShots screenshots;

	/**
	 * Author Name : Hema Sai Date of Preparation : 11-09-2016 Purpose of Method
	 * : Constructor declaration
	 */
	public WebElementFactory(RemoteWebDriver driver) {
		this.driver = driver;
		screenshots = new ScreenShots(driver);
	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 11-10-2016 Purpose of Method
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
		case "doubleclick":
			actions.doubleClick(element);
			action = actions.build();
			action.perform();
			break;
		case "contextclick":
			actions.contextClick(element);
			action = actions.build();
			action.perform();
			break;
		case "hover":
			actions.moveToElement(element);
			action = actions.build();
			action.perform();
			break;
		case "release":
			actions.release(element);
			action = actions.build();
			action.perform();
			break;
		default:
			break;
		}
	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 11-10-2016 Purpose of Method
	 * : Clears and enters text in input box
	 */
	public void enterText(String controlName, String text) {
		Actions actions = new Actions(driver);
		WebElement element = getElement(controlName);
		try {
			element.clear();
			actions.sendKeys(element, text).perform();
		} catch (Exception exception) {
			screenshots.takeScreenShots();
			Reporter.log("Element is not currently visible and so may not be interacted with "
					+ ExceptionUtils.getStackTrace(exception));
			throw new ExceptionHandling_ElementNotVisible(controlName);
		}
	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 11-10-2016 Purpose of Method
	 * : Select element from the drop down based on the value
	 */
	public void selectByValue(String controlName, String optionToBeSelected) {
		WebElement element = getElement(controlName);
		Select select = new Select(element);
		select.selectByValue(optionToBeSelected);
	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 11-10-2016 Purpose of Method
	 * : Select element from the drop down based on the value
	 */
	public void selectByValue(WebElement element, String optionToBeSelected) {
		Select select = new Select(element);
		select.selectByValue(optionToBeSelected);
	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 12-10-2016 Purpose of Method
	 * : Select element from the drop down based on the visible text
	 */
	public void selectByVisibleText(String controlName, String visibleText) {
		WebElement element = getElement(controlName);
		Select select = new Select(element);
		select.selectByValue(visibleText);
	}

	/**
	 * Author Name : Hema Sai Date of Preparation : 12-10-2016 Purpose of Method
	 * : Returns the text of the web element
	 */
	public String getElementText(String controlName) {
		WebElement element = getElement(controlName);
		String Text = element.getText();
		return Text;
	}

	/**
	 * Author Name : Srilatha Date of Preparation : 12-10-2016 Purpose of Method
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
		case "linktext":
			return By.linkText(controlProperty);
		case "partiallinktext":
			return By.partialLinkText(controlProperty);
		case "classname":
			return By.className(controlProperty);
		case "cssSelector":
			return By.cssSelector(controlProperty);
		case "tagname":
			return By.tagName(controlProperty);
		default:
			return null;
		}
	}

	/**
	 * Author Name : Srilatha Date of Preparation : 12-10-2016 Purpose of Method
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
	 * Author Name : Srilatha Date of Preparation : 13-10-2016 Purpose of Method
	 * : Checks if the elements are not present on the page
	 */
	public List<WebElement> getElementNotPresent(String controlName) {

		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlName);
		By elementLocator = getLocator(obj.getControlProperty(), obj.getTypeOfProperty());
		List<WebElement> element = driver.findElements(elementLocator);
		if (element.size() == 0) {
			Reporter.log("Elements are not present");
		} else {
			return element;
		}
		return element;
	}

	/**
	 * Author Name : Srilatha Date of Preparation : 13-10-2016 Purpose of Method
	 * : Gets the list of child elements on the page based on the parent element
	 * 
	 * @throws NoSuchElementException
	 */
	public List<WebElement> getChildElements(WebElement parent, String childControls) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(childControls);
		By locator = getLocator(obj.getControlProperty(), obj.getTypeOfProperty());
		List<WebElement> element = parent.findElements(locator);
		if (element.size() == 0) {
			screenshots.takeScreenShots();
			throw new ExceptionHandling_NoSuchElementException(childControls);
		} else {
			return element;
		}
	}

	/**
	 * Author Name : Srilatha Date of Preparation : 13-10-2016 Purpose of Method
	 * : Gets the list elements on the page based on the specified control name
	 * 
	 * @throws NoSuchElementException
	 */
	public List<WebElement> getElements(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		;
		UIControlObject obj = map.get(controlName);
		By locator = getLocator(obj.getControlProperty(), obj.getTypeOfProperty());
		List<WebElement> element = driver.findElements(locator);
		if (element.size() == 0) {
			screenshots.takeScreenShots();
			throw new ExceptionHandling_NoSuchElementException(controlName);
		} else {
			return element;
		}
	}

	/**
	 * Author Name : Suresh Date of Preparation : 11-10-2016 Purpose of Method :
	 * Checks if the frame is present or not
	 */
	public Boolean isFramePresent() {
		if (driver.findElements(By.tagName("frame")).size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Author Name : Suresh Date of Preparation : 26-12-2016 Purpose of Method :
	 * Switches to the active text box and enters text
	 */
	public void enterTextOnSelectedTextBox(String controlName, String text) {
		WebElement element = getElement(controlName);
		element.click();
		driver.switchTo().activeElement().sendKeys(text);
	}

	/**
	 * Author Name : Suresh Date of Preparation : 11-10-2016 Purpose of Method :
	 * Finds the child elements based on the xpath property
	 */
	public List<WebElement> findElementsByXpath(String elementLocator) {
		List<WebElement> element = driver.findElements(By.xpath(elementLocator));
		return element;
	}

	/**
	 * Author Name : Suresh Date of Preparation : 12-10-2016 Purpose of Method :
	 * Checks if the parent element contains the child elements
	 */
	public List<WebElement> verifyContainsChildElements(WebElement parent, String childControls) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(childControls);
		By locator = getLocator(obj.getControlProperty(), obj.getTypeOfProperty());
		List<WebElement> element = parent.findElements(locator);
		return element;
	}

	/**
	 * Author Name : Suresh Date of Preparation : 12-10-2016 Purpose of Method :
	 * Returns the child element of the specified parent element
	 * 
	 * @throws NoSuchElementException
	 */
	public WebElement getChildElement(WebElement parent, String childControls) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(childControls);
		By locator = getLocator(obj.getControlProperty(), obj.getTypeOfProperty());
		List<WebElement> element = parent.findElements(locator);
		if (element.size() == 0) {
			screenshots.takeScreenShots();
			throw new ExceptionHandling_NoSuchElementException(childControls);
		} else {
			return element.get(0);
		}
	}

	/**
	 * Author Name : Suresh Date of Preparation : 12-10-2016 Purpose of Method :
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