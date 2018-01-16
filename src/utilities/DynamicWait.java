package utilities;

import java.util.concurrent.TimeUnit;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
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

	public void waitTillPageLoads() {
		if (!(driver.equals("Internet Explorer"))) {
			try {
				new FluentWait<WebDriver>(driver).withTimeout(300, TimeUnit.SECONDS)
						.pollingEvery(5, TimeUnit.MILLISECONDS)
						.until(new com.google.common.base.Function<WebDriver, Boolean>() {
							@Override
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
