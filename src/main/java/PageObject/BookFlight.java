package PageObject;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.asserts.SoftAssert;

import com.via.constants.AppConstants;
import com.via.utils.ElementUtil;
import com.via.utils.ExcelUtil;
import com.via.utils.JavaScriptUtil;

import org.openqa.selenium.support.ui.WebDriverWait;
import reusable.ReusableMethods;
import org.openqa.selenium.JavascriptExecutor;

public class BookFlight extends ReusableMethods {

	public WebDriver driver;
	public static String date;
	String fcm;
	public String lcm;

	public int count;
	int i = 0;
	int j = 0;
	ExcelUtil excUtil = new ExcelUtil(System.getProperty("user.dir") + "\\src\\test\\java\\resource\\viadata.xlsx");
	ElementUtil eleUtil;
	JavaScriptUtil jsUtil;

	public BookFlight(WebDriver driver) {
		super(driver, date);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		eleUtil=new ElementUtil(driver);
		jsUtil=new JavaScriptUtil(driver);
	}

	@FindBy(xpath = "//input[@id='source']")
	WebElement source;

	@FindBy(xpath = "//input[@id='destination']")
	WebElement destination;

	@FindBy(xpath = "//li[@class='ui-menu-item']")
	List<WebElement> Sourcelist;

	@FindBy(xpath = "//li[@class='ui-menu-item']")
	List<WebElement> departurelist;

	@FindBy(xpath = "//div[@id='search-flight-btn']")
	WebElement searchbutton;

	@FindBys(@FindBy(xpath = "//div[@class='optsDiv']/preceding-sibling::div[@class='dealDiv'][1]"))
	List<WebElement> flightcompany;

	@FindBys(@FindBy(xpath = "//div[@class='name js-toolTip']"))
	List<WebElement> logoname;

	@FindBy(xpath = "//div[text()='100%']")
	WebElement hundred;

	// a[@class='viaLogo hideFromCustomer']
	// a[@class='viaLogo hideFromCustomer']
	// div[@class='labl modify']//div[@class='modifyCTA']
	@FindBy(xpath = "//a[@class='viaLogo hideFromCustomer']")
	WebElement modifybutton;

	List<String> flightcompanyAL = new ArrayList<String>();
	List<String> logonameAL = new ArrayList<String>();

	public void enterSource(String SourceKey, String Sourcestation){
		try {
			eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_LONG_TIME_OUT, source);
			Thread.sleep(1000);
			jsUtil.clickElementByJS(source);
			source.clear();
			source.sendKeys(SourceKey);
			// Thread.sleep(1000);
			for (WebElement selectsource : Sourcelist) {

				String name = selectsource.getText();
				
				if (name.contains(Sourcestation)) {
					Thread.sleep(2000);
					selectsource.click();
				}
			}
			
		} catch (Exception e) {
			System.out.println("Issue in BookFlight.enterSource "+e);
		}	
	}

	public void enterDestination(String key, String Destinationstation){
		try {
			eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_LONG_TIME_OUT, destination);
			destination.click();
			destination.clear();
			destination.sendKeys(key);
			Thread.sleep(2000);
			for (WebElement selectdeparture : departurelist) {

				if (selectdeparture.getText().contains(Destinationstation)) {
					Thread.sleep(2000);
					selectdeparture.click();
				}
			}
			
		} catch (Exception e) {
			System.out.println("Issue in BookFlight.enterDestination "+e);
		}
	}

	public void enterdate(String date) throws InterruptedException, IOException {

		ReusableMethods rm = new ReusableMethods(driver, date);
		rm.selectdate(date);
	}

	public void clicksearch() {
		eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_LONG_TIME_OUT, searchbutton);
		jsUtil.clickElementByJS(searchbutton);
	}

	public void findFlights(String SuplierName, String FlightName, String rowNumber){
		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[text()='100%']"))));
			jsUtil.scrollPageDown();
			Thread.sleep(10000);

			for (WebElement ifc : flightcompany) {
				fcm = ifc.getText();
				flightcompanyAL.add(i, fcm);
				i++;
			}

			count = flightcompanyAL.size();
			System.out.println("Total flight found " + count);

			for (WebElement ilc : logoname) {
				lcm = ilc.getText();
				logonameAL.add(j, lcm);
				j++;
			}

			int count2 = logonameAL.size();
			int flightfound = 0;
			System.out.println("Total logoname found " + count2);
			SoftAssert sa = new SoftAssert();

			for (int a = 0; a <= count - 1; a++) {
				if (flightcompanyAL.get(a).contains(SuplierName) && logonameAL.get(a).contains(FlightName)) {
					flightfound++;
				}
			}
			Thread.sleep(4000);
			modifybutton.click();
			System.out.println(flightfound);
			
			int rowNumberDataUpdate=Integer.parseInt(rowNumber);
			Thread.sleep(1000);
			
				if (flightfound > 0) {
					excUtil.setCellData("TestData", "Status", rowNumberDataUpdate, "Pass");
				} else {
					excUtil.setCellData("TestData", "Status", rowNumberDataUpdate, "Fail");			
			}
			sa.assertTrue(flightfound > 0, "Number of " + FlightName + flightfound);
			sa.assertAll();
			
		} catch (Exception e) {
			System.out.println("Issue in BookFlight.findFlights "+e);
		}		
	}
}
