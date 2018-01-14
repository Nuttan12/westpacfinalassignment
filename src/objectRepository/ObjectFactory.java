package objectRepository;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;
import objectRepository.UIControlObject;
import properties.LoadFrameworkProp;

public class ObjectFactory {

	public ObjectMap<String, UIControlObject> objMap;

	/**
	 * Author Name : Nuttan Abhijan Swain : This method is used to create a map
	 * of control names and property values based on the key value pair
	 */
	public void createObjectMap() {
		InputStream inp;
		Workbook wb;
		try {
			objMap = new ObjectMap<String, UIControlObject>();
			LoadFrameworkProp prop = new LoadFrameworkProp();
			inp = new FileInputStream(prop.getObjectRepository());
			wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			for (int i = 1; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				String key = row.getCell(1).getStringCellValue();
				UIControlObject uiControl = new UIControlObject();
				uiControl.setControlName(row.getCell(1).getStringCellValue());
				uiControl.setTypeOfProperty(row.getCell(2).getStringCellValue());
				uiControl.setControlProperty(row.getCell(3).getStringCellValue());
				objMap.put(key, uiControl);
			}
			inp.close();
		} catch (Exception exception) {
			Reporter.log(ExceptionUtils.getStackTrace(exception));
		}
	}

	/**
	 * Author Name : Nuttan Abhijan Swain : This method is used to return a map
	 * of control names and property values based on the key value pair
	 */
	public ObjectMap<String, UIControlObject> getObjectMap() {
		return objMap;
	}
}