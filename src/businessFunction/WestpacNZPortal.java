package businessFunction;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import properties.LoadFrameworkProp;
import testData.TestDataFactory;
import testData.TestDataMap;
import utilities.Assertions;
import utilities.DynamicWait;
import utilities.ScreenShots;
import utilities.UserActions;
import utilities.Verify;
import utilities.WebElementFactory;

public class WestpacNZPortal {
	boolean alertPresent = false;
	protected RemoteWebDriver driver;
	protected DynamicWait dynamicWait;
	protected TestDataMap<String, String> dataMap;
	protected ObjectMap<String, UIControlObject> objectMap;
	protected WebElementFactory elementFactory;
	protected ScreenShots screenshots;
	protected UserActions userActions;
	protected Verify verify;
	protected Assertions assertions;
	public WebDriverWait wait;
	public String browser;
	LoadFrameworkProp frameworkConfig = new LoadFrameworkProp();

	public WestpacNZPortal(RemoteWebDriver driver, String browser) {
		this.driver = driver;
		this.browser = browser;
		elementFactory = new WebElementFactory(driver);
		TestDataFactory dataFactory = new TestDataFactory();
		dataMap = dataFactory.createTestDataMap();
		ObjectFactory objectFactory = new ObjectFactory();
		objectMap = objectFactory.getObjectMap();
		userActions = new UserActions(driver);
		dynamicWait = new DynamicWait(driver);
		verify = new Verify(driver);
		screenshots = new ScreenShots(driver);
		assertions = new Assertions(driver);
		wait = new WebDriverWait(driver, 30);
	}

	public void newTransactionPortalLunch() throws Exception {
		try {
			if ((browser.equalsIgnoreCase("firefox")) || (browser.equalsIgnoreCase("chrome"))) {

				driver.get(frameworkConfig.getNewTransactionPortalUrl());
				verify.verifyAlert();
			} else if (browser.equalsIgnoreCase("iexplorer")) {
				driver.get(frameworkConfig.getNewTransactionPortalUrlForInternetExplorer());
			}
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
	}
}
