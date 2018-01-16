package test;


import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import properties.LoadFrameworkProp;
import testData.TestDataFactory;
import testData.TestDataMap;
import utilities.Assertions;
import utilities.DynamicWait;
import utilities.ScreenShots;
import utilities.UserActions;
import utilities.Verify;
import utilities.WebElementFactory;
import utilities.excelUtilis;
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
	protected InheritableThreadLocal<excelUtilis> excelUtil = new InheritableThreadLocal<excelUtilis>();

	protected static TestDataMap<String, String> dataMap;
	protected TestDataFactory dataFactory;
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	
	Logger log=Logger.getLogger("WestpacApplication");
	

	@Parameters({ "browser" })
	@BeforeMethod()
	public void setup(String browserName) throws Exception {
		createDirectory("HTMLReports");
		createDirectory("Screenshot");
		try {
			caps = new DesiredCapabilities();
			if (browserName.equalsIgnoreCase("iexplorer")) {
				File file = new File(".\\drivers\\IEDriverServer.exe");
				String path =file.getAbsolutePath();
				//File file = new File("D:\\Nuttan_WestpacAssignment\\westpacAssignment\\drivers\\IEDriverServer.exe");
				//System.out.println(path);
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				caps.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.westpac.co.nz/");
				//System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				System.setProperty("webdriver.ie.driver", path);
				driver = new InternetExplorerDriver(caps);
			} else if (browserName.equalsIgnoreCase("firefox")) {
				File pathToBinary = new File("C:\\Users\\" + System.getProperty("user.name")
						+ "\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				driver = new FirefoxDriver(ffBinary, firefoxProfile);

			} else if (browserName.equalsIgnoreCase("Chrome")) {
				File file = new File(".\\drivers\\chromedriver.exe");
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
			
			htmlReporter.config().setDocumentTitle("WestPacNewZealand Automation");
			htmlReporter.config().setReportName("Automation Regression Suite");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.STANDARD);
			
			PropertyConfigurator.configure("log4j.properties");
			
			File file = new File(".\\Excels\\ObjectRepository.xls");
			String path =file.getAbsolutePath();
			excelUtilis.setExcelFile(path, "TestData");
			

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
	/**
	  Author Name                       : Nuttan Abhijan Swain
	  Date of Preparation               : 12-01-2018
	  Date of Modified                  : 14-01-2018
	  Methods Called                    : newTransactionPortalLunch(),hoverOn(String ControlName)
	  									  clickOn(String controlName),waitTillPageLoads(),
	  									  getElement(String controlName),waitForElementToBeClickable(String controlName)
	  									  getElementText(String controlName)
	  Purpose of Method                 : Verify if  If there is no amount entered and the convert button is clicked then proper error message is being shown.
	  Dependencies	                    : Jar files
	  Reviewed By                       : 
	 **/


	@Test(priority = 1, description = "Validating Error Message on Currency Converter Page")
	public void ValidatingErrorMessage() throws Exception {
		
		logger= extent.createTest("ValidatingErrorMessage");
		//Launching the WestPac Newzealand Website
		log.info("Launching WestpacNewzealand Transaction Portal");
		westpacNZPortal.get().newTransactionPortalLunch();
		//To hover on FX,Travel and Migrant tab.
		userActions.get().hoverOn("LoginPage_ MenuHover");
		//To click on Currency Converter option
		log.info("Clicking on Currency converter option");
		userActions.get().clickOn("LoginPage_Currencyconverter");
		//Waiting till the page completely loads
		dynamicWait.get().waitTillPageLoads();
		dynamicWait.get().waitTime(10);
		verify.get().verifyPageTitle("PageTitle");
		//Switching to iframe present in the transaction page
		driver.switchTo().frame("westpac-iframe");
		//To clear the input amount text box
		elementFactory.get().getElement("Currencyconverter_InputAmount").clear();
		//Waiting period for the currency converter button to be clickable
        dynamicWait.get().waitForElementToBeClickable("Currencyconverter_ConvertButton");
        //Click on Convert Button
        log.info("Clicking on convert button");
		userActions.get().clickOn("Currencyconverter_ConvertButton");
		//To verify the system generated error message and expected error message are equal or not
		assertions.get().stringAssertContains(elementFactory.get().getElementText("Currencyconverter_ErrorMessage"), dataMap.get("ErrorMessage"));
		logger.addScreenCaptureFromPath("screenshot.png");
		logger.log(Status.PASS, MarkupHelper.createLabel("ValidatingErrorMessage test case is passed", ExtentColor.GREEN));
	}
	
	/**
	  Author Name                       : Nuttan Abhijan Swain
	  Date of Preparation               : 12-01-2018
	  Date of Modified                  : 14-01-2018
	  Methods Called                    : newTransactionPortalLunch(),hoverOn(String ControlName)
	  									  clickOn(String controlName),waitTillPageLoads(),
	  									  getElement(String controlName),waitForElementToBeClickable(String controlName)
	  									  getElementText(String controlName)
	  Purpose of Method                 : Verify User is able to convert one or more currency or not
	  Dependencies	                    : Jar files
	  Reviewed By                       : 
	 **/
	@Test(priority = 2, description = "Conversion Of Currency")
	public void ValidatingConversionCurrency() throws Exception {
		logger= extent.createTest("ValidatingConversionCurrency");
		//Launching the WestPac Newzealand Website
		log.info("Launching WestpacNewzealand Transaction Portal");
		westpacNZPortal.get().newTransactionPortalLunch();
		//To hover on FX,Travel and Migrant tab.
		userActions.get().hoverOn("LoginPage_ MenuHover");
		//To click on Currency Converter option
		log.info("Clicking on Currency converter option");
		userActions.get().clickOn("LoginPage_Currencyconverter");
		//Waiting till the page completely loads
		dynamicWait.get().waitTime(4);
		verify.get().verifyPageTitle("PageTitle");
		//Implemented method for Converting Newzealand Dollar to US Dollar
		log.info("Converting Newzealand Dollar to US Dollar");
		excelUtil.get();
		excelUtil.get();
		convertCurrency.get().convertCurrencyValidation(excelUtilis.getCellData(3, 0),excelUtilis.getCellData(2, 0),"1");
		//Implemented method for Converting US Dollar to Newzealand Dollar
		log.info("Converting US Dollar to Newzealand Dollar");
		excelUtil.get();
		excelUtil.get();
		convertCurrency.get().convertCurrencyValidation(excelUtilis.getCellData(2, 0),excelUtilis.getCellData(3, 0),"1");
		//Implemented method for Converting Pound Sterling to Newzealand Dollar
		log.info("Converting Pound Sterling to Newzealand Dollar");
		excelUtil.get();
		excelUtil.get();
		convertCurrency.get().convertCurrencyValidation(excelUtilis.getCellData(4, 0),excelUtilis.getCellData(3, 0),"1");
		//Implemented method for Converting  Swiss Franc to Euro
		log.info("Converting Converting  Swiss Franc to Euro");
		excelUtil.get();
		excelUtil.get();
		convertCurrency.get().convertCurrencyValidation(excelUtilis.getCellData(5, 0),excelUtilis.getCellData(6, 0),"1");
		logger.log(Status.PASS, MarkupHelper.createLabel("ValidatingConversionCurrency is passed", ExtentColor.GREEN));
		
	}	
}
