package driver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Formatter;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.LocaleUtil;

public class ExcelReader {

	public FileInputStream fis;

	public XSSFSheet sheet;
	XSSFWorkbook workbook;
	String excelPath;

	public ExcelReader(String excelPath) throws IOException {
		this.excelPath = excelPath;
		fis = new FileInputStream(excelPath);
		workbook = new XSSFWorkbook(fis);
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		int index = workbook.getSheetIndex(sheetName);
		sheet = workbook.getSheetAt(index);
		XSSFRow row = sheet.getRow(0);
		int col_Num = 0;
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
				col_Num = i;
				break;
			}
		}
		row = sheet.getRow(rowNum - 1);
		XSSFCell cell = row.getCell(col_Num);
		if (cell == null) {
			// System.out.println("cell is not present");
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		default:
			System.out.println("no matching enum date type found");
			break;
		}

		return colName;
	}

//	public static void main(String[] args) throws IOException 
//	{
//		ExcelReader reader = new ExcelReader("E:\\Ebix_Workspace\\Automatiovia\\src\\test\\java\\resource\\viadata.xlsx");
//		System.out.println(reader.getCellData("TestData", "Source", 3));
//	}

	public void write(int ro, int cel, String value) throws IOException {
		String filePath = excelPath;
		FileInputStream fileInputStream = new FileInputStream(filePath);

		// Open the workbook
		Workbook workbook = new XSSFWorkbook(fileInputStream);

		// Get the first sheet in the workbook
		Sheet sheet = workbook.getSheetAt(0);

		// Get the row and cell where you want to write the value
		Row row = sheet.getRow(ro); // Assuming you want to write to the first row
		Cell cell = row.createCell(cel); // Assuming you want to write to the first column

		// Write the value to the cell
		cell.setCellValue(value);

		// Close the input stream
		fileInputStream.close();

		// Write the changes back to the Excel file
		FileOutputStream outputStream = new FileOutputStream(filePath);
		workbook.write(outputStream);
		outputStream.close();

		// Close the workbook
		workbook.close();
	}

	
}
