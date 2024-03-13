package driver;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataaProvider {

	static DataFormatter formatter = new DataFormatter();

	@DataProvider(name = "Data")
	public static Object[][] getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\java\\resource\\viadata.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowCount = sheet.getPhysicalNumberOfRows();// total row
		XSSFRow row = sheet.getRow(0);// all cells in row 0
		int colCount = row.getLastCellNum();// total column
		Object data[][] = new Object[rowCount - 1][colCount];
		for (int i = 0; i < rowCount - 1; i++) {
			row = sheet.getRow(i + 1);
			for (int j = 0; j < colCount; j++) {
				// data[i][j]=row.getCell(j);//but here data might be of string,int,char so we
				// use DataFormatter to convert all into string.
				XSSFCell cell = row.getCell(j);
				data[i][j] = formatter.formatCellValue(cell);
			}
		}
		return data;
	}

//	@Test(dataProvider="Data")
//	public void testcaseData(String SourceKey,String Sourcestation,String DestinationKey,String Destinationstation,String Date,String FlightName,String SuplierName){
//	System.out.println(SourceKey+Sourcestation);
//	}
}
