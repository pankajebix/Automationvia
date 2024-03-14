package reusable;

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
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	public void selectdate() {
		try {
			String Dateinsheet = Date;
			String Dateinsheet1[] = Dateinsheet.split("/");
			day = Dateinsheet1[0];
			Month = Dateinsheet1[1];
			year = Dateinsheet1[2];

			String monthyear;

			while (true) {

				Thread.sleep(1000);
				monthyear = driver.findElement(By.xpath("//span[@class='vc-month-box-head-cell ']")).getText();

				String arr[] = monthyear.split(" ");
				String mon = arr[0];
				String yr = arr[1];

				if (mon.equalsIgnoreCase(Month) && yr.equals(year))
					break;
				else
					Thread.sleep(2000);
				WebElement ele2 = driver.findElement(By.xpath(
						"//span[@class='vc-month-box-head-cell vc-month-controls icon-Rightarrowthin vc-month-control-active js-next-month']"));
				eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_MEDIUM_TIME_OUT, ele2);
				jsUtil.clickElementByJS(ele2);

				System.out.println("clicked on arrow");
			}

			Thread.sleep(1000);
			WebElement date = driver.findElement(By.xpath(
					"(//div[@class='vc-month-box-container'])[1]//div[not(@class='vc-cell vc-disabled-cell') and @data-date='"
							+ day + "'] "));

			eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_MEDIUM_TIME_OUT, date);
			jsUtil.scrollIntoViewTrue(date);
			jsUtil.clickElementByJS(date);

		} catch (Exception e) {
			System.out.println("Issue in ReusableMethods.selectdate " + e);
		}
	}

}