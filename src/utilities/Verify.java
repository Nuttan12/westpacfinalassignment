/**
 * 
 */
package utilities;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import testData.TestDataFactory;
import testData.TestDataMap;
import utilities.Assertions;
import utilities.DynamicWait;
import utilities.ScreenShots;
import utilities.WebElementFactory;

/**
 * Author Name : Nuttan Abhijan 
 * Represents the functions which are used to handle various verifications performed on the Application under Test
 */
public class Verify {

	private WebElementFactory elementFactory;
	private UserActions userActions;
	private ScreenShots screenshots = null;
	private Alert alert = null;
	private TestDataMap<String, String> dataMap;
	protected TestDataFactory dataFactory;
	protected RemoteWebDriver driver;
	protected DynamicWait dynamicWait;
	protected Assertions assertions;

	/**
	 * 
	 * Constructor declaration
	 */
	public Verify(RemoteWebDriver driver) {
		this.driver = driver;
		userActions = new UserActions(driver);
		elementFactory = new WebElementFactory(driver);
		dataFactory = new TestDataFactory();
		dataMap = dataFactory.createTestDataMap();
		dynamicWait = new DynamicWait(driver);
		screenshots = new ScreenShots(driver);
		assertions = new Assertions(driver);
	}

	/**
	 * 
	 * Checks if the element is not present on the page
	 */
	public void isElementNotPresent(String controlName) {
		List<WebElement> elements = elementFactory.getElementNotPresent(controlName);
		if (elements.size() > 0) {
			screenshots.takeScreenShots();
			Assert.fail(controlName + "is present on the page");
		}
	}

	/**
	 * 
	 * Checks if the element is present on the page
	 */
	public void isElementPresent(String controlName) {
		List<WebElement> elements = elementFactory.getElements(controlName);
		if (elements.size() == 0) {
			screenshots.takeScreenShots();
			Reporter.log("Element with controlName " + controlName + " is not present on the page");
			Assert.fail(controlName + " is not present on the page");
		}
		Reporter.log(controlName + " present on the page");
	}

	/**
	 * 
	 * Checks if the element is enabled on the page
	 */
	public boolean isElementEnabled(String controlName) {
		WebElement element = elementFactory.getElement(controlName);

		if (!element.isEnabled() && element.getAttribute("class").contains("disable")) {
			screenshots.takeScreenShots();
			Assert.fail(controlName + " is not enabled on the page");
			Reporter.log("Element is not enabled on the page");
			return false;
		}
		Reporter.log(controlName + " Element is enabled on the page");
		return true;
	}

	/**
	 * 
	 * Checks if the element is disabled on the page
	 */
	public boolean isElementDisabled(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		if (element.isEnabled() && !(element.getAttribute("class").contains("disable"))) {
			screenshots.takeScreenShots();
			Assert.fail(controlName + " is not disabled on the page");
			Reporter.log("Element not disabled on the page");
			return false;
		}
		Reporter.log(controlName + " is disabled on the page");
		return true;
	}
	/**
	 * 
	 * Checks if the element is displayed on the page
	 */
	public boolean isElementDisplayed(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		if ((!element.isDisplayed())) {
			screenshots.takeScreenShots();
			Reporter.log("Element not displayed on the page" + element.getText());
			Assert.fail(controlName + " is not displayed on the page");
			return false;
		}
		Reporter.log(controlName + " displayed on the page");
		return true;
	}

	/**
	 * 
	 * Checks if the alert is present
	 * 
	 * @throws Exception
	 */
	public void verifyAlert() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			Reporter.log(alert.getText());
			alert.accept();
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
	}

	/**
	 * 
	 * Verifies the actual page title matches the expected page title
	 */
	public void verifyPageTitle(String controlName) {
		dynamicWait.waitTillPageLoads();
		String title_expected = dataMap.get(controlName);
		String title_actual = driver.getTitle();
		assertions.stringAssertEquals(title_actual, title_expected);
		Reporter.log("Sucessfully navigated to the " + title_actual);
	}
	/**
	 * 
	 * Verifies if the text is present
	 */
	public void verifyTextPresent(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		String Value = element.getText();
		if (Value.isEmpty()) {
			screenshots.takeScreenShots();
			Assert.fail("The " + controlName + " value is not present ");
			Reporter.log("Default value is not present in the page");
		} else {
			Reporter.log("Default value " + Value + " is present in the page");
		}
	}

}
