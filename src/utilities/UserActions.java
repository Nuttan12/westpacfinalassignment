/**
 * 
 */
package utilities;

import java.util.List;
import java.util.Set;

import objectRepository.ObjectFactory;
import objectRepository.UIControlObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import exceptions.ExceptionHandling_InvalidElementStateException;
import testData.TestDataFactory;
import testData.TestDataMap;
import utilities.DynamicWait;
import utilities.WebElementFactory;

public class UserActions {

	protected WebElementFactory elementFactory;
	private String parentWindow = null;
	protected DynamicWait dynamicWait;
	private Alert alert = null;
	private RemoteWebDriver driver;
	private int timer = 1;
	public TestDataMap<String, String> dataMap;
	protected ScreenShots screenshots;

	public UserActions(RemoteWebDriver driver) {
		this.driver = driver;
		elementFactory = new WebElementFactory(driver);
		dynamicWait = new DynamicWait(driver);
		TestDataFactory dataFactory = new TestDataFactory();
		dataMap = dataFactory.createTestDataMap();
		screenshots = new ScreenShots(driver);
	}


	public void clickOn(String controlName) {
		try {
			dynamicWait.waitForElementToBeClickable(controlName);
			elementFactory.doMouseAction(controlName, "click");
		} catch (Exception exception) {
			screenshots.takeScreenShots();
			Reporter.log(ExceptionUtils.getStackTrace(exception));
			throw new ExceptionHandling_InvalidElementStateException(controlName);
		}
	}

	public void hoverOn(String controlName) {
		elementFactory.doMouseAction(controlName, "hover");
	}


	public void switchToParentWindow() {
		driver.switchTo().window(parentWindow);
	}

	public boolean isChildWindowOpen() {

		Set<String> windows = driver.getWindowHandles();
		if (windows.size() == 1) {
			return false;
		}
		return true;
	}


	public WebElement getElement(String controlName) {
		return elementFactory.getElement(controlName);
	}

	public String getText(String controlName) {
		return elementFactory.getElement(controlName).getText();
	}


	public UIControlObject getUIControlObject(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		return factory.getObjectMap().get(controlName);
	}
}
