package test;


import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import properties.LoadFrameworkProp;
import testData.TestDataFactory;
import testData.TestDataMap;
import utilities.Assertions;
import utilities.DynamicWait;
import utilities.ScreenShots;
import utilities.UserActions;
import utilities.Verify;
import utilities.WebElementFactory;
import businessFunction.WestpacNZPortal;
import businessFunction.ConvertCurrency;

public class westPacApplication {
	
	protected InheritableThreadLocal<RemoteWebDriver> threadDriver = null;
	protected DesiredCapabilities caps;
	protected RemoteWebDriver driver = null;
	LoadFrameworkProp frameworkConfig = new LoadFrameworkProp();
	protected InheritableThreadLocal<UserActions> userActions = new InheritableThreadLocal<UserActions>();
	protected InheritableThreadLocal<Verify> verify = new InheritableThreadLocal<Verify>();
	protected InheritableThreadLocal<DynamicWait> dynamicWait = new InheritableThreadLocal<DynamicWait>();
	protected InheritableThreadLocal<Assertions> assertions = new InheritableThreadLocal<Assertions>();
	protected InheritableThreadLocal<WebElementFactory> elementFactory = new InheritableThreadLocal<WebElementFactory>();
	protected InheritableThreadLocal<ScreenShots> screenshots = new InheritableThreadLocal<ScreenShots>();
	protected InheritableThreadLocal<WestpacNZPortal> westpacNZPortal = new InheritableThreadLocal<WestpacNZPortal>();
	protected InheritableThreadLocal<ConvertCurrency> convertCurrency = new InheritableThreadLocal<ConvertCurrency>();

	protected static TestDataMap<String, String> dataMap;
	protected TestDataFactory dataFactory;
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;

	@Parameters({ "browser" })
	@BeforeMethod()
	public void setup(String browserName) throws Exception {
		createDirectory("HTMLReports");
		createDirectory("Screenshot");
		try {
			caps = new DesiredCapabilities();
			if (browserName.equalsIgnoreCase("iexplorer")) {
				File file = new File("D:\\Nuttan_WestpacAssignment\\westpacAssignment\\drivers\\IEDriverServer.exe");
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				caps.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.westpac.co.nz/");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				driver = new InternetExplorerDriver(caps);
			} else if (browserName.equalsIgnoreCase("firefox")) {
				File pathToBinary = new File("C:\\Users\\" + System.getProperty("user.name")
						+ "\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				driver = new FirefoxDriver(ffBinary, firefoxProfile);

			} else if (browserName.equalsIgnoreCase("Chrome")) {
				File file = new File("D:\\Nuttan_WestpacAssignment\\westpacAssignment\\drivers\\chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
				caps = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");
				options.addArguments("ignore-certificate-errors");
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(caps);
			}
			try {
				threadDriver = new InheritableThreadLocal<RemoteWebDriver>();
				threadDriver.set(driver);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} catch (Exception exception) {
				Reporter.log(
						"There is some issue while launching the browser " + ExceptionUtils.getStackTrace(exception));
			}
			dataFactory = new TestDataFactory();
			dataMap = dataFactory.createTestDataMap();

			verify.set(new Verify(driver));
			assertions.set(new Assertions(driver));
			userActions.set(new UserActions(driver));
			dynamicWait.set(new DynamicWait(driver));
			screenshots.set(new ScreenShots(driver));
			elementFactory.set(new WebElementFactory(driver));

			westpacNZPortal.set(new WestpacNZPortal(driver, browserName));
			convertCurrency.set(new ConvertCurrency(driver));
			
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/STMExtentReport.html");
			htmlReporter.setAppendExisting(true);
			extent = new ExtentReports ();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Environment", "Automation Testing");
			extent.setSystemInfo("User Name", "Nuttan Abhijan Swain");
			

		} catch (Exception exception) {
			Reporter.log("Browser Setup Failure " + ExceptionUtils.getStackTrace(exception));
		}
	}

	public static void createDirectory(String directoryName) {
		File projectDirectory = new File(System.getProperty("user.dir"));
		File newDirectory = new File(projectDirectory, directoryName);
		if (!newDirectory.exists()) {
			newDirectory.mkdir();
		}
	}
	
	@AfterMethod
	public void logStatus(ITestResult result) throws Exception {
		try {
			if (result.getStatus() == ITestResult.FAILURE)
				logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
				screenshots.get().takeScreenShots(result.getTestName());
				extent.flush();
				driver.quit();		
			
		} catch (Exception exception) {
			Reporter.log("Error occurred while quitting the browser " + ExceptionUtils.getStackTrace(exception));
		}
	}

	@AfterTest
	public void teardown() throws Exception {
		try {	
			
		} catch (Exception exception) {
			Reporter.log("Error occurred while quitting the browser " + ExceptionUtils.getStackTrace(exception));
		}
	}

	@Test(priority = 1, description = "Validating Error Message on Currency Converter Page")
	public void ValidatingErrorMessage() throws Exception {
		
		logger= extent.createTest("ValidatingErrorMessage");
		westpacNZPortal.get().newTransactionPortalLunch();
		userActions.get().hoverOn("LoginPage_ MenuHover");
		userActions.get().clickOn("LoginPage_Currencyconverter");
		dynamicWait.get().waitTillPageLoads();
		dynamicWait.get().waitTime(10);
		driver.switchTo().frame("westpac-iframe");
		elementFactory.get().getElement("Currencyconverter_InputAmount").clear();
        dynamicWait.get().waitForElementToBeClickable("Currencyconverter_ConvertButton");
		userActions.get().clickOn("Currencyconverter_ConvertButton");
		assertions.get().stringAssertContains(elementFactory.get().getElementText("Currencyconverter_ErrorMessage"), dataMap.get("ErrorMessage"));
		logger.log(Status.PASS, MarkupHelper.createLabel("TValidatingErrorMessage test case is passed", ExtentColor.GREEN));
	}
	
	@Test(priority = 2, description = "Conversion Of Currency")
	public void ValidatingConversionCurrency() throws Exception {
		//second commit again hello
		logger= extent.createTest("ValidatingConversionCurrency");
		westpacNZPortal.get().newTransactionPortalLunch();
		userActions.get().hoverOn("LoginPage_ MenuHover");
		userActions.get().clickOn("LoginPage_Currencyconverter");
		dynamicWait.get().waitTime(4);
		convertCurrency.get().convertCurrencyValidation("NZ","US","1");
		convertCurrency.get().convertCurrencyValidation("US","NZ","1");
		convertCurrency.get().convertCurrencyValidation("PS","NZ","1");
		convertCurrency.get().convertCurrencyValidation("SF","EU","1");
		logger.log(Status.PASS, MarkupHelper.createLabel("ValidatingConversionCurrency is passed", ExtentColor.GREEN));
		
	}	
}
