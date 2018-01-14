package utilities;

import java.util.concurrent.TimeUnit;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import exceptions.ExceptionHandling_TimeoutException;

public class DynamicWait {
	protected WebElementFactory elementFactory;
	protected RemoteWebDriver driver;
	protected ScreenShots screenshots;

	/**
	 * 
	 * : Constructor declaration
	 */
	public DynamicWait(RemoteWebDriver driver) {
		this.driver = driver;
		elementFactory = new WebElementFactory(driver);
		screenshots = new ScreenShots(driver);
	}

	public void waitForElementToBeClickable(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlName);
		By by = elementFactory.getLocator(obj.getControlProperty().toString(), obj.getTypeOfProperty());
		try {
			new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeClickable(by));
		} catch (TimeoutException time) {
			screenshots.takeScreenShots();
			Reporter.log("Element " + controlName + "is not found on time " + ExceptionUtils.getStackTrace(time));
			throw new ExceptionHandling_TimeoutException(controlName);
		}
	}

	public void waitForElementToBeSelected(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		;
		UIControlObject obj = map.get(controlName);
		By by = elementFactory.getLocator(obj.getControlProperty().toString(), obj.getTypeOfProperty());
		try {
			new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class).until(ExpectedConditions.elementToBeSelected(by));
		} catch (TimeoutException time) {
			screenshots.takeScreenShots();
			Reporter.log("Element " + controlName + "is not found on time " + ExceptionUtils.getStackTrace(time));
			throw new ExceptionHandling_TimeoutException(controlName);
		}

	}

	public void waitForFrameToBeAvailableAndSwitchToIt(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		;
		UIControlObject obj = map.get(controlName);
		By by = elementFactory.getLocator(obj.getControlProperty().toString(), obj.getTypeOfProperty());
		try {
			new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchFrameException.class).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
		} catch (TimeoutException time) {
			screenshots.takeScreenShots();
			Reporter.log("Element " + controlName + "is not found on time " + ExceptionUtils.getStackTrace(time));
			throw new ExceptionHandling_TimeoutException(controlName);
		}
	}

	public void waitForPresenceOfElementLocated(String controlName) {

		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		;
		UIControlObject obj = map.get(controlName);
		By by = elementFactory.getLocator(obj.getControlProperty().toString(), obj.getTypeOfProperty());
		try {
			new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
					.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (TimeoutException time) {
			screenshots.takeScreenShots();
			Reporter.log("Element " + controlName + "is not found on time " + ExceptionUtils.getStackTrace(time));
			throw new ExceptionHandling_TimeoutException(controlName);
		}

	}

	public void waitForAlertIsPresent() {
		new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class).until(ExpectedConditions.alertIsPresent());
	}

	public void waitForVisibilityOfElementLocated(String controlName) {
		ObjectFactory factory = new ObjectFactory();
		factory.createObjectMap();
		ObjectMap<String, UIControlObject> map = factory.getObjectMap();
		UIControlObject obj = map.get(controlName);
		By by = elementFactory.getLocator(obj.getControlProperty().toString(), obj.getTypeOfProperty());
		try {
			new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (TimeoutException time) {
			screenshots.takeScreenShots();
			Reporter.log("Element " + controlName + "is not found on time " + ExceptionUtils.getStackTrace(time));
			throw new ExceptionHandling_TimeoutException(controlName);
		}

	}

	public void waitUntilAttributeChanges(final WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String active = element.getAttribute("class");
				if (active.contains("active"))
					return true;
				else
					return false;
			}
		});
	}

	public void waitForChildWindows() {
		try {
			waitTime(5);
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
	}

	public void waitUntilProgressCompletes(String controlName) {
		long timeoutInSeconds = 120;
		new WebDriverWait(driver, timeoutInSeconds)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.className(controlName)));
	}

	public void waitTillPageLoads() {
		if (!(driver.equals("Internet Explorer"))) {
			try {
				new FluentWait<WebDriver>(driver).withTimeout(300, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.MILLISECONDS)
						.until(new com.google.common.base.Function<WebDriver, Boolean>() {
							public Boolean apply(WebDriver driver) {
								return ((JavascriptExecutor) driver).executeScript("return document.readyState")
										.equals("complete");
							}
						});
			} catch (Exception exception) {
				screenshots.takeScreenShots();
				Reporter.log(ExceptionUtils.getStackTrace(exception));
			}
		} else {
			try {
				waitTime(10);
			} catch (Exception exception) {
				screenshots.takeScreenShots();
				Reporter.log(ExceptionUtils.getStackTrace(exception));
			}
		}
	}

	public void waitTime(double seconds) {
		try {
			Thread.sleep((long) (seconds * 1000L));
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
	}
}
