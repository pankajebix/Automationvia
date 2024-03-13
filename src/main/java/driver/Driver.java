package driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.via.utils.SendEmail;

import PageObject.Login;
import cn.t.util.email.EmailClientUtil;

public class Driver {

	public WebDriver driver = null;

	@BeforeClass
	public WebDriver getDriver() throws InterruptedException, IOException {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://id.via.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		Login log = new Login(driver);
		log.userlogin();
		return driver;
	}

	@AfterClass
	public void close() {
		driver.quit();
		SendEmail.main(null);
	}
}
