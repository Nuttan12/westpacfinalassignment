/**
 * 
 */
package utilities;

import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import objectRepository.ObjectFactory;
import objectRepository.UIControlObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import exceptions.ExceptionHandling_InvalidElementStateException;
import exceptions.ExceptionHandling_NoSuchWindowException;
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

	public void enterData(String controlName, String text) {
		try {
			dynamicWait.waitTime(0.6);
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
		elementFactory.enterText(controlName, text);
	}

	public void clickAlertOk() {
		alert = driver.switchTo().alert();
		alert.accept();
	}

	public void clearTextBox(String controlName) {
		elementFactory.doMouseAction(controlName, "clear");
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

	public void enterTextOnActiveElement(String controlName, String text) {
		String inputText = dataMap.get(text);
		elementFactory.enterTextOnSelectedTextBox(controlName, inputText);
	}

	public void clickAndHold(String controlName) {
		elementFactory.doMouseAction(controlName, "clickandhold");
	}

	public void selectValueFromDropDown(String ControlName, String dataName) {
		String dataElement = dataMap.get(dataName);
		WebElement element = elementFactory.getElement(ControlName);
		List<WebElement> list = element.findElements(By.tagName("li"));
		for (int i = 0; i < list.size(); i++) {
			if (dataElement.trim().equals(list.get(i).getText().trim())) {
				list.get(i).click();
				break;
			}
		}
	}

	public void doubleClick(String controlName) {
		elementFactory.doMouseAction(controlName, "doubleclick");
	}

	public void contextClick(String controlName) {
		elementFactory.doMouseAction(controlName, "contextclick");
	}

	public void hoverOn(String controlName) {
		elementFactory.doMouseAction(controlName, "hover");
	}

	public void hoverOn(WebElement controlName) {
		Actions actions = new Actions(driver);
		Action action;
		actions.moveToElement(controlName);
		action = actions.build();
		action.perform();
	}

	public void release(String controlName) {
		elementFactory.doMouseAction(controlName, "release");
	}

	public void clickOnElement(WebElement element) {
		try {
			element.click();
		} catch (NoSuchElementException exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
	}

	public void enterText(String controlname, String text) {
		String inputText = dataMap.get(text);
		elementFactory.enterText(controlname, inputText);
	}

	public void switchToChildWindow() {
		try {
			dynamicWait.waitTime(8);
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
		Set<String> windows = driver.getWindowHandles();
		if (windows.size() != 1) {
			parentWindow = driver.getWindowHandle();
			windows.remove(parentWindow);
			driver.switchTo().window(windows.iterator().next());
			driver.manage().window().maximize();
		} else {
			if (timer == 1) {
				try {
					dynamicWait.waitTime(8);
				} catch (Exception exception) {
					Reporter.log(ExceptionUtils.getStackTrace(exception));
				}
				timer++;
				switchToChildWindow();
			} else {
				screenshots.takeScreenShots();
				throw new ExceptionHandling_NoSuchWindowException();
			}
		}

	}

	public void selectFromDropdownMenu(String ControlName, String text) {
		WebElement element = elementFactory.getElement(ControlName);
		List<WebElement> list = element.findElements(By.tagName("li"));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().trim().contains(text.trim())) {
				list.get(i).click();
				break;
			}
		}
	}

	public void selectFromDropdownMenu(WebElement element, String text) {
		List<WebElement> list = element.findElements(By.tagName("li"));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().trim().contains(text.trim())) {
				list.get(i).click();
				break;
			}
		}
	}

	public void selectFromList(WebElement element, String text) {
		List<WebElement> list = element.findElements(By.tagName("option"));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().trim().contains(text.trim())) {
				list.get(i).click();
				break;
			}
		}
	}

	public void displayValuesInDropdownMenu(String ControlName) {
		WebElement element = elementFactory.getElement(ControlName);
		List<WebElement> list = element.findElements(By.tagName("li"));
		for (int i = 0; i < list.size(); i++) {
			Reporter.log("The Dropdown values are" + list.get(i).getText());

		}
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

	public void closeChildWindow() {
		if (parentWindow == null) {
			switchToChildWindow();
			driver.close();
			switchToParentWindow();
		} else {
			driver.close();
			switchToParentWindow();
		}
	}

	public List<WebElement> getElements(String controlName) {
		return elementFactory.getElements(controlName);
	}

	public WebElement getElement(String controlName) {
		return elementFactory.getElement(controlName);
	}

	public String getText(String controlName) {
		return elementFactory.getElement(controlName).getText();
	}

	public String getCssProperty(String controlName, String cssAttribute) {
		return elementFactory.getElement(controlName).getCssValue(cssAttribute);
	}

	public String getCssProperty(WebElement uiControl, String cssAttribute) {
		return uiControl.getCssValue(cssAttribute);
	}

	public String getHtmlAttribute(String controlName, String htmlAttribute) {
		return elementFactory.getElement(controlName).getAttribute(htmlAttribute);
	}

	public String getHtmlAttribute(WebElement element, String htmlAttribute) {
		return element.getAttribute(htmlAttribute);
	}

	public UIControlObject getUIControlObject(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		return factory.getObjectMap().get(controlName);
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public void switchToFrame(String frameName) {
		WebElement frame = elementFactory.getElement(frameName);
		driver.switchTo().frame(frame);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public void selectByText(String controlName, String text) {
		new Select(elementFactory.getElement(controlName)).selectByVisibleText("text");
	}

	public void scriptClickOn(String controlName) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement element = elementFactory.getElement(controlName);
		executor.executeScript("arguments[0].click();", element);
	}

	public void scriptClickOnElement(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void selectOptionWithText(String controlName, String textToSelect) {
		WebElement element = elementFactory.getElement(controlName);
		List<WebElement> list = element.findElements(By.tagName("li"));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().trim().contains(textToSelect.trim())) {
				list.get(i).click();
				break;
			}
		}
	}

	public void selectDifferentValueFromDropdownMenu(String ControlName, String text) {
		WebElement element = elementFactory.getElement(ControlName);
		List<WebElement> list = element.findElements(By.tagName("li"));
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getText().trim().isEmpty()
					&& !(list.get(i).getText().trim()).contains(text.trim().substring(0, 8))) {
				list.get(i).click();
				break;
			}
		}
	}

	public void verifyValueFromDropDown(String ControlName) {
		WebElement element = elementFactory.getElement(ControlName);
		List<WebElement> list = element.findElements(By.tagName("li"));
		for (int i = 0; i < list.size() - 1; i++) {
			if (!list.get(i).getText().isEmpty() || !list.get(i).getAttribute("data-value").isEmpty()) {
				list.get(i).isDisplayed();
				Reporter.log("Dropdown Contains values");
			} else {
				screenshots.takeScreenShots();
				Assert.fail("Dropdown is empty");
			}
		}
	}

	public void enterDataOnActiveElement(String controlName, String text) {
		elementFactory.enterTextOnSelectedTextBox(controlName, text);
	}

	public void clickonCheckBox(String ControlName) {
		elementFactory.getElement(ControlName).click();
		// list.get(1).click();
	}

}
