package businessFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
import utilities.excelUtilis;

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

	public String getPrivilaege(String RowValue) {
		try {
			excelUtilis.setExcelFile("D:\\Nuttan_Eclipse\\Syngenta_Report\\Excels\\Book2.xls", "TestData");
			CellData = excelUtilis.getExactCellData(RowValue);
			System.out.println(CellData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CellData;
	}

	public void convertCurrencyValidation(String fromCurrency,String ToCurrency,String val) {
		

		dynamicWait.waitTillPageLoads();
		driver.switchTo().frame("westpac-iframe");
		WebDriverWait wait = new WebDriverWait(driver,10);
		Select convertFrom = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id("ConvertFrom"))));
		Select convertTo = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id("ConvertTo"))));
		convertFrom.selectByVisibleText(dataMap.get(fromCurrency));
		elementFactory.getElement("Currencyconverter_InputAmount").sendKeys(val);
		convertTo.selectByVisibleText(dataMap.get(ToCurrency));
		userActions.clickOn("Currencyconverter_ConvertButton");
		dynamicWait.waitTime(4);
		driver.switchTo().defaultContent();
		driver.navigate().refresh();
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
