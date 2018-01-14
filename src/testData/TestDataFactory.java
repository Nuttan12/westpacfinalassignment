package testData;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;

import properties.LoadFrameworkProp;
import utilities.Verify;

/**
 * 
 * Represents the Test data repository used to act on the Application under Test
 */
public class TestDataFactory {

	public TestDataMap<String, String> testDataMap;
	protected Verify verify;

	/**
	 * 
	 * : This method is used to create a test data map based on the key value
	 * pair
	 */
	public TestDataMap<String, String> createTestDataMap() {
		try {
			testDataMap = new TestDataMap<String, String>();
			LoadFrameworkProp prop = new LoadFrameworkProp();
			InputStream inp = new FileInputStream(prop.getTestDataFile());
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheet("TestData");
			int rowNum = sheet.getLastRowNum();
			for (int i = 1; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				String key = row.getCell(0).getStringCellValue();
				String value = row.getCell(1).toString(); // .replace(".0", "");
				testDataMap.put(key, value);
			}
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
		return testDataMap;
	}

	/**
	 * 
	 * : This method is used to return the test data based on the key value pair
	 */
	public TestDataMap<String, String> getTestMap() {
		return testDataMap;
	}

}
