package businessFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import objectRepository.ObjectFactory;
import objectRepository.ObjectMap;
import objectRepository.UIControlObject;
import properties.LoadFrameworkProp;
import testData.TestDataFactory;
import testData.TestDataMap;
import utilities.Assertions;
import utilities.DynamicWait;
import utilities.ScreenShots;
import utilities.UserActions;
import utilities.Verify;
import utilities.WebElementFactory;
import utilities.Log;

public class ConvertCurrency {
	String CellData;
	boolean alertPresent = false;
	protected RemoteWebDriver driver;
	protected DynamicWait dynamicWait;
	protected TestDataMap<String, String> dataMap;
	protected ObjectMap<String, UIControlObject> objectMap;
	protected WebElementFactory elementFactory;
	protected ScreenShots screenshots;
	protected UserActions userActions;
	protected Log log;
	protected Verify verify;
	protected Assertions assertions;
	public WebDriverWait wait;
	public String browser;
	LoadFrameworkProp frameworkConfig = new LoadFrameworkProp();

	List<String> set1 = new ArrayList<String>();
	List<String> set2 = new ArrayList<String>(Arrays.asList("Save", "Save And Close", "Refresh", "Close"));

	public ConvertCurrency(RemoteWebDriver driver) {
		this.driver = driver;
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
	
	/**
	  Author Name                       : Nuttan Abhijan Swain
	  Date of Preparation               : 12-01-2018
	  Date of Modified                  : 14-01-2018
	  Methods Called                     :
	  									  clickOn(String controlName),waitTillPageLoads(),
	  									  getElement(String controlName),waitForElementToBeClickable(String controlName)
	  									  getElementText(String controlName)
	  Purpose of Method                 : Verify User is able to convert one or more currency or not
	  Dependencies	                    : Jar files
	  Reviewed By                       : 
	 **/

	public void convertCurrencyValidation(String fromCurrency,String ToCurrency,String val) {
		

		dynamicWait.waitTillPageLoads();
		driver.switchTo().frame("westpac-iframe");
		WebDriverWait wait = new WebDriverWait(driver,10);
		Select convertFrom = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id("ConvertFrom"))));
		Select convertTo = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id("ConvertTo"))));
		convertFrom.selectByVisibleText(dataMap.get(fromCurrency));
		Log.info("Entering amount to be converted");
		elementFactory.getElement("Currencyconverter_InputAmount").sendKeys(val);
		convertTo.selectByVisibleText(dataMap.get(ToCurrency));
		Log.info("Clicking on Convert Button");
		userActions.clickOn("Currencyconverter_ConvertButton");
		dynamicWait.waitTime(4);
		//System.out.println(val+" "+dataMap.get(fromCurrency));
		if (assertions.stringAssertContains(elementFactory.getElementText("Currencyconverter_ValidationMessage"), val+" "+dataMap.get(fromCurrency)) &&
				assertions.stringAssertContains(elementFactory.getElementText("Currencyconverter_ValidationMessage"), dataMap.get(ToCurrency)) &&	
				assertions.stringAssertContains(elementFactory.getElementText("Currencyconverter_ValidationMessage"),"Rates updated"))
		{	
		driver.switchTo().defaultContent();
		Log.info("Refreshing the webpage");
		driver.navigate().refresh();
		}
	}
	
	public boolean validatingInputBox(String controlName) {
		
		driver.switchTo().frame("westpac-iframe");
		boolean bul = false;
		String textInsideInputBox = elementFactory.getElementText(controlName);
		// Check whether input field is blank
		if(textInsideInputBox.isEmpty())
		{
		   System.out.println("Input field is empty");
		   bul = true;
		}
		return bul;

	}

}
