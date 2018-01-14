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
 * Author Name : Nuttan Abhijan Represents the functions which are used to
 * handle various verifications performed on the Application under Test
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
	 * Checks if the text is present on the page
	 */
	public void isTextPresent(String controlName) {
		WebElement labelElement = elementFactory.getElement(controlName);
		String actual = labelElement.getText().trim();
		actual = actual.replaceAll("[^\\p{ASCII}]", " ").replace(".0", "").replaceAll(" ", "");
		String expected = dataMap.get(controlName).toString().trim();
		expected = expected.replaceAll("[^\\p{ASCII}]", " ").replaceAll(" ", "");
		assertions.stringAssertEquals(actual, expected);
	}

	/**
	 * 
	 * Checks if the results are displayed based on a certain value
	 */
	public void verifySearchResults(List<WebElement> resultElements, String keyword) {
		for (WebElement element : resultElements) {
			if (!element.getText().contains(keyword)) {
				screenshots.takeScreenShots();
				Assert.fail("Test failed since the results are not being displayed with the keyword " + keyword);
			}
		}
	}

	/**
	 * 
	 * Checks if the pagination is performed
	 */
	public void verifyPagination(List<String> elements, WebElement element) {
		if (elements.contains(element.getText())) {
			screenshots.takeScreenShots();
			Assert.fail(
					"Test failed since the contents in the second page match with the contents in the first page which is not valid");
		}
	}

	/**
	 * 
	 * Checks if the element on the page contains specific text
	 */
	public void verifyElementContainsText(String controlName, String controlNameExpected) {
		WebElement element = elementFactory.getElement(controlName);
		String actual = element.getText().toLowerCase();
		String expected = dataMap.get(controlNameExpected).toLowerCase();
		if (!actual.contains(expected)) {
			screenshots.takeScreenShots();
			Assert.fail("The String does not contains " + dataMap.get(controlNameExpected));
		}
	}

	/**
	 * 
	 * Checks if the elements are displayed on the page
	 */
	public boolean elementsDisplayed(String controlName) {
		List<WebElement> elements = elementFactory.getElements(controlName);
		for (WebElement element : elements) {
			if (!element.isDisplayed()) {
				screenshots.takeScreenShots();
				Reporter.log("Element is not displayed on the page" + element.getText());
				Assert.fail(controlName + "is not displayed on the page");
				return false;
			}
			Reporter.log(controlName + element.getText() + " displayed on the page");
		}
		return true;
	}

	/**
	 * 
	 * Checks if the element is not displayed on the page
	 */
	public boolean isElementNotDisplayed(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		if (element.isDisplayed()) {
			Reporter.log("Element displayed on the page");
			screenshots.takeScreenShots();
			Assert.fail(controlName + " is displayed on the page");
			return false;
		}
		Reporter.log("Element is not displayed on the page");
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
	 * Checks if the elements are displayed on the page
	 */
	public boolean areElementsDisplayed(String controlName) {
		List<WebElement> elements = elementFactory.getElements(controlName);
		for (WebElement element : elements) {
			if (!(element.isDisplayed())) {
				screenshots.takeScreenShots();
				Reporter.log("Elements are not displayed on the page" + elements.lastIndexOf(elements));
				Assert.fail(controlName + " are not displayed on the page");
				return false;
			}
		}
		Reporter.log(controlName + "are displayed on the page");
		return true;
	}

	/**
	 * 
	 * Checks if the elements are not displayed on the page
	 */
	public boolean areElementsNotDisplayed(String controlName) {
		List<WebElement> elements = elementFactory.getElements(controlName);
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				screenshots.takeScreenShots();
				Reporter.log("Elements are displayed on the page" + elements.lastIndexOf(elements));
				Assert.fail(controlName + " are displayed on the page");
				return false;
			}
		}
		Reporter.log(controlName + "are not displayed on the page");
		return true;
	}

	/**
	 * 
	 * Verifies if the element is selected on the page
	 */
	public boolean isElementSelected(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		if (!element.isSelected()) {
			screenshots.takeScreenShots();
			Reporter.log("Element is not selected on the page" + element.getText());
			Assert.fail(controlName + "is not selected on the page");
			return false;
		}
		Reporter.log(controlName + " selected on the page");
		return true;
	}

	/**
	 * 
	 * Checks if the text is in a regular font
	 */
	public void isFontRegular(String controlName) {
		dynamicWait.waitTillPageLoads();
		WebElement element = elementFactory.getElement(controlName);
		if (element.getText().length() > 0) {
			if (!element.getCssValue("font-size").toString().trim().equals("100%")
					&& !element.getCssValue("font-size").toString().trim().equals(dataMap.get(controlName))) {
				screenshots.takeScreenShots();
				Assert.fail(" Font size " + element.getCssValue("font-size") + " for the text under ControlName : "
						+ controlName + " is not matching the expected value: " + dataMap.get(controlName));
			}
		} else {
			screenshots.takeScreenShots();
			Assert.fail("No text found under element with controlName:" + controlName);
		}
	}

	/**
	 * 
	 * Checks the font style for the text
	 */
	public void isFontStyle(String controlName) {
		dynamicWait.waitTillPageLoads();
		WebElement element = elementFactory.getElement(controlName);
		String value = dataMap.get(controlName);
		if (element.getText().length() > 0) {
			if (!element.getCssValue("font-family").toString().trim().equalsIgnoreCase(value)) {
				screenshots.takeScreenShots();
				Assert.fail(" Font style " + element.getCssValue("font-family") + " for the text under ControlName : "
						+ controlName + " is not matching the expected value: " + value);
			}
		} else {
			screenshots.takeScreenShots();
			Assert.fail("No text found under element with controlName:" + controlName);
		}
	}

	/**
	 * 
	 * Checks if the element is selected on the page
	 */
	public boolean checkElementSelected(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		if (element.getAttribute("checked") != null) {
			if (element.getAttribute("checked").equalsIgnoreCase("true")) {
				Reporter.log(controlName + " selected on the page");
				return true;
			} else {
				Reporter.log("Element is not selected on the page" + element.getText());
				return false;
			}
		} else {
			Reporter.log("Element is not selected on the page" + element.getText());
			return false;
		}
	}

	/**
	 * 
	 * Checks the text on the alert
	 */
	public void verifyAlertText(String expectedValue) {
		alert = driver.switchTo().alert();
		assertions.stringAssertEquals(alert.getText(), dataMap.get(expectedValue));
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
	 * : Verify Text presence in line item level
	 */
	public String isLineItemTextPresent(String controlName) {
		String lineItemText = null;
		List<WebElement> lineItem = userActions.getElements(controlName);
		for (int i = 0; i < lineItem.size(); i++) {
			if (!lineItem.get(i).getText().isEmpty()) {
				lineItemText = lineItem.get(i).getText();
				break;
			}
		}
		return lineItemText;
	}

	/**
	 * 
	 * Checks if the element is checked or selected
	 */
	public void verifyElementIsChecked(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		if (!element.isSelected()) {
			screenshots.takeScreenShots();
			Assert.fail("The " + controlName + "is not checked");
		}
	}

	/**
	 * 
	 * Checks if the element is not checked or selected
	 */
	public void verifyElementIsNotChecked(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		if (element.isSelected()) {
			screenshots.takeScreenShots();
			Assert.fail("The " + controlName + "is checked");
		}
	}

	/**
	 * 
	 * Verifies the actual string length matches the expected string length
	 */
	public void verifyStringLength(String controlName, int expectedLength) {
		WebElement element = elementFactory.getElement(controlName);
		String actualLength = element.getAttribute("value");
		assertions.numericAssertEquals(actualLength.length(), expectedLength);
	}

	/**
	 * 
	 * Verifies if the specified text on the page is present in capital letters
	 */
	public void verifyTextPresentInCaps(String controlName) {
		WebElement element = elementFactory.getElement(controlName);
		String element1 = element.getText().trim().replace(" ", "");
		if (!(element1.matches("(\\b[A-Z][A-Z0-9]+\\b)"))) {
			screenshots.takeScreenShots();
			Assert.fail("The " + controlName + " 's text is not present in block letters");
		}
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

	/**
	 * 
	 * : The function is used to verify if the integer elements are in ascending
	 * order
	 */
	public void isNumberSortedAscending(List<Integer> elements) {
		List<Integer> lists = new ArrayList<Integer>(elements);
		lists.remove("");
		List<Integer> list = new ArrayList<>(lists);
		for (int i = 1; i < list.size(); i++) {
			if ((list.get(i - 1)) > (list.get(i)) && !((list.get(i - 1)) == (list.get(i)))) {
				screenshots.takeScreenShots();
				Assert.fail("Elements are not sorted in ascending order");
			}
		}
	}

	/**
	 * 
	 * : The function is used to verify if the integer elements are in
	 * descending order
	 */
	public void isNumberSortedDescending(List<Integer> elements) {
		List<Integer> lists = new ArrayList<Integer>(elements);
		lists.remove("");
		List<Integer> list = new ArrayList<>(lists);
		for (int i = 1; i < list.size(); i++) {
			if ((list.get(i - 1)) < (list.get(i)) && !((list.get(i - 1)) == (list.get(i)))) {
				screenshots.takeScreenShots();
				Assert.fail("Elements are not sorted in descending order");
			}
		}
	}

	/**
	 * 
	 * : The function is used to verify if the elements are in descending order
	 */
	public <T extends Comparable<? super T>> void isSortedDescending(List<T> elements) {
		List<T> allItems = new ArrayList<T>(elements);
		allItems.removeAll(Arrays.asList("", null));
		List<T> list = new ArrayList<T>(allItems);
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i - 1).compareTo(list.get(i)) < 0) {
				screenshots.takeScreenShots();
				Assert.fail("Elements are not sorted in descending order");
			} else if ((list.get(i - 1).compareTo(list.get(i)) == 0) || (list.get(i - 1).compareTo(list.get(i)) > 0)) {
				continue;
			}
		}
	}

	/**
	 * 
	 * : The function is used to verify if the elements are in ascending order
	 */
	public <T extends Comparable<? super T>> void isSortedAscending(List<T> elements) {
		List<T> allItems = new ArrayList<T>(elements);
		allItems.removeAll(Arrays.asList("", null));
		List<T> list = new ArrayList<T>(allItems);
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i - 1).compareTo(list.get(i)) > 0) { // the value 0 if
																// the argument
																// string is
																// equal to this
																// string; a
																// value less
																// than 0 if
																// this string
																// is
																// lexicographically
																// less than the
																// string
																// argument; and
																// a value
																// greater than
																// 0 if this
																// string is
																// lexicographically
																// greater than
																// the string
																// argument.
				screenshots.takeScreenShots();
				Assert.fail("Elements are not sorted in ascending order");
			} else if ((list.get(i - 1).compareTo(list.get(i)) == 0) || (list.get(i - 1).compareTo(list.get(i)) < 0)) {
				continue;
			}
		}
	}

	/**
	 * 
	 * : The function is used to verify if the elements are in ascending order
	 */
	@SuppressWarnings("unchecked")
	public static <T> void removeSpecialCharacters(List<T> set) {
		List<String> copy = new ArrayList<>();
		for (T str : set) {
			str = (T) ((String) str).replaceAll("[.!@#$%^&()_+-=]", "");
			copy.add((String) str);
		}
		set.clear();
		set.addAll((Collection<? extends T>) copy);
	}

	/**
	 * 
	 * : Verifies the data of the child elements
	 */
	public void verifyChildElementsData(String controlName, String childElement) {
		WebElement parentElement = elementFactory.getElement(controlName);
		List<WebElement> childElements = elementFactory.getChildElements(parentElement, childElement);
		String dataElement = dataMap.get(controlName);
		List<String> childDataElements = new ArrayList<String>(Arrays.asList(dataElement.split(",")));
		int count = 0;
		for (WebElement element : childElements) {
			if (element.isDisplayed()) {
				if (!(element.getText().equals(childDataElements.get(count)))) {
					screenshots.takeScreenShots();
					Reporter.log(element.getText() + " element is not available");
					Assert.fail(element.getText() + " element is not available");
				}
			}
			count++;
		}
		Reporter.log("All the elements are available");
	}

	/**
	 * 
	 * : Verifies the data of the elements
	 */
	public void verifyElementsData(String controlName) {
		dynamicWait.waitTillPageLoads();
		List<WebElement> childElements = elementFactory.getElements(controlName);
		String dataElement = dataMap.get(controlName);
		List<String> childDataElements = new ArrayList<String>(Arrays.asList(dataElement.split(",")));
		int count = 0;
		for (int i = 0; i < childDataElements.size(); i++) {
			for (int j = 0; j < childElements.size(); j++) {
				if (childElements.get(j).isDisplayed()) {
					if (childElements.get(j).getText().equals(childDataElements.get(i))) {
						count++;
					}
				}
			}
			if (!(count == (i + 1))) {
				screenshots.takeScreenShots();
				Reporter.log(childElements.get(i).getText() + " element is not available");
				Assert.fail(childElements.get(i).getText() + " element is not available");
			}
		}
		Reporter.log("All the elements are available");
	}

	/**
	 * 
	 * : Compares the list of elements with the known data
	 */
	public void verifyFilterByText(String controlName, String text) {
		List<WebElement> childElements = elementFactory.getElements(controlName);
		for (WebElement element : childElements) {
			if (!element.getText().contains(dataMap.get(text))) {
				screenshots.takeScreenShots();
				Reporter.log("Article " + element.getText() + " is not filtered with the given text");
				Assert.fail("Article " + element.getText() + " is not filtered with the given text");
			}
		}
		Reporter.log("Filter By text is done properly");
	}

	/**
	 * 
	 * : The function is used to verify if the date elements are in ascending
	 * order
	 * 
	 * @throws ParseException
	 */
	public void isDateSortedAscending(List<String> elements) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<String> allItems = new ArrayList<String>(elements);
		allItems.removeAll(Arrays.asList("", null));
		List<String> list = new ArrayList<String>(allItems);
		for (int i = 1; i < list.size(); i++) {
			Date previous = format.parse(list.get(i - 1));
			Date next = format.parse(list.get(i));
			if (previous.compareTo(next) > 0) {
				screenshots.takeScreenShots();
				Assert.fail("Elements are not sorted in ascending order");
			} else if ((list.get(i - 1).compareTo(list.get(i)) == 0) || (list.get(i - 1).compareTo(list.get(i)) < 0)) {
				continue;
			}
		}
	}

	/**
	 * 
	 * : The function is used to verify if the date elements are in descending
	 * order
	 * 
	 * @throws ParseException
	 */
	public void isDateSortedDescending(List<String> elements) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<String> allItems = new ArrayList<String>(elements);
		allItems.removeAll(Arrays.asList("", null));
		List<String> list = new ArrayList<String>(allItems);
		for (int i = 1; i < list.size(); i++) {
			Date previous = format.parse(list.get(i - 1));
			Date next = format.parse(list.get(i));
			if (previous.compareTo(next) < 0) {
				screenshots.takeScreenShots();
				Assert.fail("Elements are not sorted in descending order");
			} else if ((list.get(i - 1).compareTo(list.get(i)) == 0) || (list.get(i - 1).compareTo(list.get(i)) > 0)) {
				continue;
			}
		}
	}

	/**
	 * 
	 * : The function is used to verify if lists of two array lists are equal
	 * 
	 * @throws ParseException
	 */
	public void areListsOfListsEqual(List<ArrayList<String>> attributesFromCartPage,
			List<ArrayList<String>> attributesFromTemplatePage) throws ParseException {
		if (attributesFromCartPage.containsAll(attributesFromTemplatePage)
				&& attributesFromTemplatePage.containsAll(attributesFromCartPage)) {
			Reporter.log("Both the lists are equal");
		} else {
			screenshots.takeScreenShots();
			Assert.fail("Elements from two lists are not equal");
		}
	}

	/**
	 * 
	 * : The function is used to verify if two array lists are equal or not
	 * 
	 * @throws ParseException
	 */
	public void areListsEqual(List<String> list1, List<String> list2) throws ParseException {
		if (list1.containsAll(list2) && list2.containsAll(list1)) {
			Reporter.log("Both the lists are equal");
		} else {
			screenshots.takeScreenShots();
			Assert.fail("Elements from two lists are not equal");
		}
	}

	/**
	 * 
	 * : The function is used to verify whether one list contains another list
	 * 
	 * @throws ParseException
	 */
	public void containsAnotherList(List<String> list1, List<String> list2) throws ParseException {
		if (list1.containsAll(list2)) {
			Reporter.log("Elements from the first list contains elements from the second list");
		} else {
			screenshots.takeScreenShots();
			Assert.fail("Elements from the first list does not elements from the second list");
		}
	}

	/**
	 * 
	 * Checks if the element on the page contains specific text
	 */
	public void verifyElementContainsStringText(String controlName, String stringExpected) {
		List<WebElement> elements = elementFactory.getElements(controlName);
		for (WebElement element : elements) {
			if (!element.getText().isEmpty()) {
				String actual = element.getText().toLowerCase();
				if (!actual.contains(stringExpected.toLowerCase())) {
					screenshots.takeScreenShots();
					Assert.fail("The String does not contains " + stringExpected);
				}
			}
		}

	}

}
