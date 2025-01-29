package baseClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pageClasses.HomePage;

public class pageBaseClass extends BaseClass{
	public ExtentTest logger;

	public pageBaseClass(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}
	
	/****************** OpenApplication ***********************/
	public HomePage OpenApplication() {
		logger.log(Status.INFO, "Opening the WebSite");
		driver.get("https://www.justdial.com/");
		logger.log(Status.PASS, "Successfully Opened the https://www.justdial.com/");
		HomePage homePage = new HomePage(driver, logger);
		PageFactory.initElements(driver, homePage);
		return homePage;
	}
}
