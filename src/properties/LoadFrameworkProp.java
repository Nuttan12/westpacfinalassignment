/**
 * 
 */
package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Reporter;

public class LoadFrameworkProp {
	private String testDataFile;
	private String objectRepository;
	private String tableName;
	private String locatorSheetNo;
	private String htmlReport;
	private String testNgResultsXml;
	private String screenshots;
	private String testDataSheetNo;
	private String newTransactionPortalUrl;
	private String newTransactionPortalUrlForInternetExplorer;
	private String username;
	private String password;
	private String configSheetNo;
	private String testCaseSheetNo;
	private String testExecutionSheetNo;
	private String testUserOne;
	private String testUserTwo;
	private String testUserThree;

	public String getUserName() {
		return username;
	}

	public String getUserNameOne() {
		return testUserOne;
	}

	public String getUserNameTwo() {
		return testUserTwo;
	}

	public String getUserNameThree() {
		return testUserThree;
	}

	public String getPassword() {
		return password;
	}

	public String getTestDataSheetNo() {
		return testDataSheetNo;
	}

	public String getLocatorSheetNo() {
		return locatorSheetNo;
	}

	public String getTestDataFile() {
		return testDataFile;
	}

	public String getObjectRepository() {
		return objectRepository;
	}

	public String getTableName() {
		return tableName;
	}

	public String getHtmlReport() {
		return htmlReport;
	}

	public String getTestNgResultsXML() {
		return testNgResultsXml;
	}

	public String getScreenshot() {
		return screenshots;
	}

	public String getNewTransactionPortalUrl() {
		return newTransactionPortalUrl;
	}

	public String getNewTransactionPortalUrlForInternetExplorer() {
		return newTransactionPortalUrlForInternetExplorer;
	}

	public String getConfigSheetNo() {
		return configSheetNo;
	}

	public String getTestCaseSheetNo() {
		return testCaseSheetNo;
	}

	public String getTestExecutionSheetNo() {
		return testExecutionSheetNo;
	}

	public LoadFrameworkProp() {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			File config = new File("config.properties");
			is = new FileInputStream(config);
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
			is = null;
		}
		try {
			if (is == null) {
				is = getClass().getResourceAsStream("config.properties");
			}
			prop.load(is);
			testDataFile = prop.getProperty("TestDataFile");
			objectRepository = prop.getProperty("ObjectRepository");
			tableName = prop.getProperty("tableName");
			locatorSheetNo = (prop.getProperty("LocatorSheetNo"));
			htmlReport = prop.getProperty("HTMLReports");
			testNgResultsXml = prop.getProperty("TestNgResultsXML");
			screenshots = prop.getProperty("Screenshots");
			newTransactionPortalUrl = prop.getProperty("WestPac_Url");
			//newTransactionPortalUrlForInternetExplorer = prop.getProperty("Syngenta_Url_IE");
			username = prop.getProperty("Username");
			password = prop.getProperty("Password");
			configSheetNo = prop.getProperty("ConfigSheetNo");
			testCaseSheetNo = prop.getProperty("TestCase");
			testExecutionSheetNo = prop.getProperty("TestExecutionSheetNo");
			testUserOne = prop.getProperty("TestUserOne");
			testUserTwo = prop.getProperty("TestUserTwo");
			testUserThree = prop.getProperty("TestUserThree");
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
	}
}