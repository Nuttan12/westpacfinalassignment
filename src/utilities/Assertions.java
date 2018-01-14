package utilities;

import java.io.File;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * 
 * Represents the functions which are used to handle assertions
 */
public class Assertions {

	protected RemoteWebDriver driver;
	private ScreenShots screenshots;

	/**
	 * 
	 * : Constructor declaration
	 */
	public Assertions(RemoteWebDriver driver) {
		this.driver = driver;
		screenshots = new ScreenShots(driver);
	}

	/**
	 * 
	 * : Checks whether the actual string is equal to expected string
	 */
	public boolean stringAssertEquals(String str_actual, String str_expected) {
		try {
			Assert.assertTrue(str_actual.trim().equalsIgnoreCase(str_expected));
			return true;
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test failed since Expected value is not equal to actual.Expected value:" + str_expected
					+ " Actual value:" + str_actual + ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test failed since Expected value is not equal to actual.Expected value:" + str_expected
					+ " Actual value:" + str_actual);
		}
		return false;
	}

	/**
	 * 
	 * : Checks whether the actual string is not expected string
	 */
	public boolean stringAssertNotEquals(String str_actual, String str_expected) {
		try {
			Assert.assertFalse(str_actual.trim().equalsIgnoreCase(str_expected));
			return true;
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test failed since Expected value is equal to actual.Expected value:" + str_expected
					+ " Actual value:" + str_actual + ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test failed since Expected value is equal to actual.Expected value:" + str_expected
					+ " Actual value:" + str_actual);
		}
		return false;
	}

	/**
	 * 
	 * : Checks whether the expected string contains actual string
	 * 
	 * @throws AssertionError
	 */
	public boolean stringAssertContains(String str_actual, String str_expected) {
		if (str_actual.contains(str_expected)) {
			return true;
		} else {
			screenshots.takeScreenShots();
			Reporter.log("Test failed as actual value does not contain expected value. Expected value:" + str_expected
					+ " Actual value:" + str_actual);
			Assert.fail("Test Failed since actual value does not contain expected. Expected value:" + str_expected
					+ " Actual value:" + str_actual);
			return false;
		}
	}

	/**
	 * 
	 * : Checks whether the actual string contains expected string
	 * 
	 * @throws AssertionError
	 */
	public boolean stringAssertContainsExpected(String str_actual, String str_expected) {
		if (str_expected.contains(str_actual)) {
			return true;
		} else {
			screenshots.takeScreenShots();
			Reporter.log("Test failed as actual value does not contain expected value. Expected value:" + str_expected
					+ " Actual value:" + str_actual);
			Assert.fail("Test Failed since actual value does not contain expected. Expected value:" + str_expected
					+ " Actual value:" + str_actual);
			return false;
		}
	}

	/**
	 * : Checks whether the actual value is not null
	 */
	public void assertNotNull(WebElement element) {
		try {
			Assert.assertNotEquals(element.getText(), "");
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test failed since the element " + element + " does not contain any value "
					+ ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since the Element " + element + " does not contain any value");
		}
	}

}
