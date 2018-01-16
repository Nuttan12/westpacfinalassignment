/**
 * 
 */
package utilities;

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
	 * Verifies the actual page title matches the expected page title
	 */
	public void verifyPageTitle(String controlName) {
		dynamicWait.waitTillPageLoads();
		String title_expected = dataMap.get(controlName);
		String title_actual = driver.getTitle();
		assertions.stringAssertEquals(title_actual, title_expected);
		Reporter.log("Sucessfully navigated to the " + title_actual);
	}

}
