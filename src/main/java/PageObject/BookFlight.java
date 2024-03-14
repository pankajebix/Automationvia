package PageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.via.constants.AppConstants;
import com.via.utils.ElementUtil;
import com.via.utils.ExcelUtil;
import com.via.utils.JavaScriptUtil;
import reusable.ReusableMethods;

public class BookFlight extends ReusableMethods {

	public WebDriver driver;
	public static String date;

	ExcelUtil excUtil = new ExcelUtil(System.getProperty("user.dir") + "\\src\\test\\java\\resource\\viadata.xlsx");
	ElementUtil eleUtil;
	JavaScriptUtil jsUtil;

	public BookFlight(WebDriver driver) {
		super(driver, date);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	@FindBy(xpath = "//input[@id='source']")
	WebElement source;

	@FindBy(xpath = "//input[@id='destination']")
	WebElement destination;

	@FindBys(@FindBy(xpath = "//li[@class='ui-menu-item']"))
	List<WebElement> Sourcelist;

	@FindBy(xpath = "//li[@class='ui-menu-item']")
	List<WebElement> departurelist;

	@FindBy(xpath = "//div[@id='search-flight-btn']")
	WebElement searchbutton;

	@FindBys(@FindBy(xpath = "//div[@class='optsDiv']/preceding-sibling::div[@class='dealDiv'][1]"))
	List<WebElement> flightcompany;

	@FindBys(@FindBy(xpath = "//div[@id='resultFilter']/div/div[@class='filt_typ airlines']/div[@class='filtDataCont']/div/label"))
	List<WebElement> flightName;

	@FindBy(xpath = "//div[text()='100%']")
	WebElement hundred;

	@FindBy(xpath = "//a[@class='viaLogo hideFromCustomer']")
	WebElement modifybutton;

	@FindBy(xpath = "//div[text()='100%']")
	private WebElement ele100;

	List<String> flightNameArrayList = new ArrayList<String>();

	public void enterSource(String SourceKey, String Sourcestation) {
		try {
			eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_LONG_TIME_OUT, source);
			jsUtil.clickElementByJS(source);
			source.clear();
			source.sendKeys(SourceKey);

			Thread.sleep(1000);

			for (WebElement selectsource : Sourcelist) {
				String name = selectsource.getText();

				if (name.contains(Sourcestation)) {
					selectsource.click();
				}
			}

		} catch (Exception e) {
			System.out.println("Issue in BookFlight.enterSource " + e);
		}
	}

	public void enterDestination(String key, String Destinationstation) {
		try {
			eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_LONG_TIME_OUT, destination);
			destination.click();
			destination.clear();
			destination.sendKeys(key);

			Thread.sleep(1000);
			for (WebElement selectdeparture : departurelist) {
				if (selectdeparture.getText().contains(Destinationstation)) {
					selectdeparture.click();
				}
			}

		} catch (Exception e) {
			System.out.println("Issue in BookFlight.enterDestination " + e);
		}
	}

	public void enterdate(String date) throws InterruptedException, IOException {

		ReusableMethods rm = new ReusableMethods(driver, date);
		rm.selectdate();
	}

	public void clicksearch() {
		eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_LONG_TIME_OUT, searchbutton);
		jsUtil.clickElementByJS(searchbutton);
	}

	public void findFlights(String FlightName, String rowNumber) {
		try {
			eleUtil.waitForElementVisible(ele100, AppConstants.DEFAULT_VERY_LONG_TIME_OUT);

		} catch (Exception e) {
			System.out.println("Issue in BookFlight.findFlights " + e);
		} finally {

			int j = 0;
			int flightFound = 0;

			int flightSize = flightName.size();
			System.out.println("Total Flight Found : " + flightSize);

			for (WebElement ele : flightName) {
				String flightNameText = ele.getText();
				System.out.println("Flight Name found on UI : " + flightNameText);

				flightNameArrayList.add(j, flightNameText);
				j++;
			}

			for (int i = 0; i < flightSize; i++) {
				if (flightNameArrayList.get(i).contains(FlightName)) {
					System.out.println("Given flight name " + FlightName + " is found.");
					flightFound = flightFound + 1;
					break;
				}
			}
			eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_MEDIUM_TIME_OUT, modifybutton);
			modifybutton.click();

			int rowNumberDataUpdate = Integer.parseInt(rowNumber);

			if (flightFound > 0) {
				excUtil.setCellData("TestData", "Status", rowNumberDataUpdate, "Pass");
				System.out.println("=========================================================");
			} else {
				excUtil.setCellData("TestData", "Status", rowNumberDataUpdate, "Fail");
				System.out.println("Given flight name " + FlightName + " is not found.");
				System.out.println("=========================================================");
			}
			Assert.assertTrue(flightFound > 0);
		}

	}
}
