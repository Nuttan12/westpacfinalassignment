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
	 * 
	 * : Checks whether the actual number is equal to expected number
	 */
	public void numericAssertEquals(int iactual, int iexpected) {
		try {
			Assert.assertEquals(iactual, iexpected);
			Reporter.log("Test passed as expected value is equal to actual value");
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test failed as expected value is not equal to actual value. Expected value:" + iexpected
					+ " Actual value:" + iactual + ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since Expected value is not equal to actual.Expected value:" + iexpected
					+ " Actual value:" + iactual);
		}
	}

	/**
	 * 
	 * : Checks whether the actual number is equal to expected number
	 */
	public void numericAssertEquals(double iactual, double iexpected) {
		try {
			Assert.assertEquals(iactual, iexpected);
			Reporter.log("Test passed as expected value is equal to actual value");
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test failed as expected value is not equal to actual value. Expected value:" + iexpected
					+ " Actual value:" + iactual + ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since Expected value is not equal to actual.Expected value:" + iexpected
					+ " Actual value:" + iactual);
		}
	}

	/**
	 * 
	 * : Checks the alignment of the element
	 */
	public void Assertequals_positionleft_cssvalue(String position) {
		try {
			Assert.assertEquals(position, "left");
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test Failed since Position of the Element is not on left "
					+ ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since Position of the Element is not on left");
		}
	}

	/**
	 * 
	 * : Checks whether the required element is right to the specified element
	 */
	public void Assert_positionright_coordinates(WebElement elementleft, WebElement elementright,
			String relativeControlName, String controlName) {
		try {
			Assert.assertTrue((elementright.getLocation().x) > (elementleft.getLocation().x));
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test Failed since the Element " + controlName + " is not aligned to right side of the page "
					+ ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since the Element " + controlName + " is not aligned to right side of the page");
		}
	}

	/**
	 * 
	 * : Checks whether the required element is left to the specified element
	 */
	public void Assert_positionleft_coordinates(WebElement elementleft, WebElement elementright,
			String relativeControlName, String controlName) {
		try {
			Assert.assertTrue((elementleft.getLocation().x) < (elementright.getLocation().x));
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test Failed since Position of the Element " + relativeControlName
					+ " is not on left to the Position of the element " + controlName
					+ ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since Position of the Element " + relativeControlName
					+ " is not on left to the Position of the element " + controlName);
		}
	}

	/**
	 * 
	 * : Checks whether the required element is aligned vertically equal with
	 * the specified element
	 */
	public void Assert_positionverticalequal_coordinates(WebElement elementabove, WebElement elementbelow,
			String relativeControlName, String controlName) {
		try {
			Assert.assertEquals((elementabove.getLocation().x), (elementbelow.getLocation().x));
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test Failed since the Element " + controlName
					+ " is not aligned vertically equal with the Element " + relativeControlName
					+ ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since the Element " + controlName
					+ " is not aligned vertically equal with the Element " + relativeControlName);
		}
	}

	/**
	 * 
	 * : Checks whether the required element is aligned horizontally equal with
	 * the specified element
	 */
	public void Assert_positionhorizantalequal_coordinates(WebElement elementabove, WebElement elementbelow,
			String relativeControlName, String controlName) {
		try {
			Assert.assertEquals((elementabove.getLocation().y), (elementbelow.getLocation().y));
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log(
					"Test Failed since the Element " + controlName + " is not aligned horizontally with the Element "
							+ relativeControlName + ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since the Element " + controlName
					+ " is not aligned horizontally with the Element " + relativeControlName);
		}
	}

	/**
	 * : Checks whether the required element is above the specified element
	 */
	public void Assert_positionabove_coordinates(WebElement elementbelow, WebElement elementabove,
			String relativeControlName, String controlName) {
		try {
			Assert.assertTrue((elementbelow.getLocation().y) > (elementabove.getLocation().y));
		} catch (AssertionError exception) {
			screenshots.takeScreenShots();
			Reporter.log("Test Failed since the Element " + controlName + " is not placed in correct order "
					+ ExceptionUtils.getStackTrace(exception));
			Assert.fail("Test Failed since the Element " + controlName + " is not placed in correct order");
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

	/**
	 * : Checks whether the required file is downloaded or not
	 */
	public String isFileDownloaded(String downloadPath, String fileName) {

		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		String fName = null;
		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().contains(fileName))
				fName = dir_contents[i].getName();
			else if (i == dir_contents.length - 1) {
				screenshots.takeScreenShots();
				Reporter.log("Failed to download the required file");
				Assert.fail("Failed to download the required file");
			}
		}
		return fName;
	}

}
