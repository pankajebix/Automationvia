package reusable;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.via.constants.AppConstants;
import com.via.utils.ElementUtil;
import com.via.utils.JavaScriptUtil;

import org.openqa.selenium.WebElement;

public class ReusableMethods {
	public WebDriver driver;
	public String year;
	public String Month;
	public String day;
	public String Date;
	
	ElementUtil eleUtil;
	JavaScriptUtil jsUtil;

	public ReusableMethods(WebDriver driver, String Date) {
		this.driver = driver;
		this.Date = Date;
		eleUtil=new ElementUtil(driver);
		jsUtil=new JavaScriptUtil(driver);
	}

	public void selectdate(String Date) throws InterruptedException, IOException {
		// month year selection
		// departuredate.click();
//		ExcelReader reader = new ExcelReader("E:\\Ebix_Workspace\\Automatiovia\\src\\test\\java\\resource\\viadata.xlsx");
//		String Dateinsheet=reader.getCellData("TestData", "Date", 2);
		String Dateinsheet = Date;
		String Dateinsheet1[] = Dateinsheet.split("/");
		day = Dateinsheet1[0];
		Month = Dateinsheet1[1];
		year = Dateinsheet1[2];

		String monthyear;
		Thread.sleep(2000);
		while (true) {

			Thread.sleep(4000);
			monthyear = driver.findElement(By.xpath("//span[@class='vc-month-box-head-cell ']")).getText();

			// System.out.println(monthyear +"gettext");
			String arr[] = monthyear.split(" ");
			String mon = arr[0];
			String yr = arr[1];
			// System.out.println(mon);

			if (mon.equalsIgnoreCase(Month) && yr.equals(year))
				break;
			else
				Thread.sleep(4000);
			WebElement ele2=driver.findElement(By.xpath(
					"//span[@class='vc-month-box-head-cell vc-month-controls icon-Rightarrowthin vc-month-control-active js-next-month']"));
			eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_MEDIUM_TIME_OUT, ele2);
			jsUtil.clickElementByJS(ele2);
			
			System.out.println("clicked on arrow");

		}
		// "(//div[@class='vc-cell ' and @data-date='"+day+"'])[1]"
		Thread.sleep(1000);
		WebElement date = driver.findElement(By.xpath(
				"(//div[@class='vc-month-box-container'])[1]//div[not(@class='vc-cell vc-disabled-cell') and @data-date='"
						+ day + "'] "));
		//date.click();
		eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_MEDIUM_TIME_OUT, date);
		jsUtil.scrollIntoViewTrue(date);
		jsUtil.clickElementByJS(date);

		// (//div[@class='vc-month-box-container'])[1]//div[@class='vc-cell ' and
		// @data-date='29']
	}

}
