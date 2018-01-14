package testData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import properties.LoadFrameworkProp;

/**
 * 
 * Represents the data to be written to the specified cell in the work sheet
 */
public class DataWriting {

	/**
	 * 
	 * This method is used to write the data in the blank row of the specified
	 * cell name
	 */
	public void WriteTestData(String fieldName, String value) throws Exception {
		LoadFrameworkProp prop = new LoadFrameworkProp();
		InputStream inp = new FileInputStream(prop.getTestDataFile());
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(2);
		int i = sheet.getLastRowNum();
		int j = 0;
		int flag = 0;
		j = i + 1;
		TestDataFactory dataFactory = new TestDataFactory();
		dataFactory.createTestDataMap();
		TestDataMap<String, String> obj = dataFactory.getTestMap();
		if (obj.keySet().contains(fieldName)) {
			obj.put(fieldName, value);
		}
		if (flag == 0) {
			Row newRow = sheet.createRow(j);
			newRow.createCell(1).setCellValue(fieldName);
			newRow.createCell(2).setCellValue(value);

		}
		FileOutputStream file = new FileOutputStream(prop.getTestDataFile());
		wb.write(file);
		file.close();

	}

}
