package pageClasses;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.BaseClass;

public class ForCampusPage extends BaseClass {
	public ExtentTest logger;

	public ForCampusPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	@FindBy(xpath = "//a[@data-track-component='EntWebsiteHow_c4c_homepage_hero_content_button_0']")
	WebElement contactUsBtn;

	// clicks on Contact us button
	public ContactSalesPage clickContactUs() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(contactUsBtn));
//		executeJS("arguments[0].scrollIntoView()", contactUsBtn);
		elementClickViaHover(contactUsBtn);

		ContactSalesPage contactSalesPage = new ContactSalesPage(driver, logger);
		PageFactory.initElements(driver, contactSalesPage);
		return contactSalesPage;
	}

}
