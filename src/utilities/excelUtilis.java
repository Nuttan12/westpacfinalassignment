package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class excelUtilis {
	private static HSSFSheet ExcelWSheet;
	private static HSSFWorkbook ExcelWBook;
	private static HSSFCell Cell;
	public static void setExcelFile(String Path, String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// Access the required test data sheet
			ExcelWBook = new HSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}

	public static String getExactCellData(String RowValue) {
		String rowMatch = null;
		int rows1 = ExcelWSheet.getFirstRowNum();
		int rows2 = ExcelWSheet.getLastRowNum();
		for (int i = rows1; i <= rows2; i++) {
			Cell = ExcelWSheet.getRow(i).getCell(0);
			if (RowValue.contains(Cell.getStringCellValue())) {
				Cell = ExcelWSheet.getRow(i).getCell(1);
				rowMatch = Cell.getStringCellValue();
			}
		}
		return rowMatch;
	}

	public static String getFolderStructureData(String RowValue) {
		String rowMatch = null;
		int rows1 = ExcelWSheet.getFirstRowNum();
		int rows2 = ExcelWSheet.getLastRowNum();
		for (int i = rows1; i <= rows2; i++) {
			Cell = ExcelWSheet.getRow(i).getCell(0);
			if (RowValue.contains(Cell.getStringCellValue())) {
				Cell = ExcelWSheet.getRow(i).getCell(2);
				rowMatch = Cell.getStringCellValue();
			}
		}
		return rowMatch;
	}
	
	public static String getNodeValueData(String RowValue) {
		String rowMatch = null;

		int rows1 = ExcelWSheet.getFirstRowNum();
		int rows2 = ExcelWSheet.getLastRowNum();
		for (int i = rows1; i <= rows2; i++) {
			Cell = ExcelWSheet.getRow(i).getCell(0);
			if (RowValue.contains(Cell.getStringCellValue())) {
				Cell = ExcelWSheet.getRow(i).getCell(3);
				rowMatch = Cell.getStringCellValue();
			}
		}
		return rowMatch;
	}

}