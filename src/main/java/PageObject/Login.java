package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.via.constants.AppConstants;
import com.via.utils.ElementUtil;

public class Login {
	public WebDriver driver;
	ElementUtil eleUtil;

	public Login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		eleUtil=new ElementUtil(driver);
	}

	@FindBy(xpath = "//input[@placeholder='Username']")
	WebElement username;

	@FindBy(xpath = "//input[@placeholder='Password']")
	WebElement password;

	@FindBy(xpath = "//input[@value='Masuk']")
	WebElement loginbutton;

	public void userlogin() throws InterruptedException {

		eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_LONG_TIME_OUT, username);
		username.clear();
		username.sendKeys("renu.sharma@via.com");
		
		password.clear();
		password.sendKeys("Ebix@2024");
		eleUtil.waitForElementToBeClickable(AppConstants.DEFAULT_SHORT_TIME_OUT, loginbutton);
		loginbutton.click();
	}
}
