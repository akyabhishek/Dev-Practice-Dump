package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.BaseClass;

public class ForBusinessPage  extends BaseClass{
	public ExtentTest logger;

	public ForBusinessPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}
	@FindBy(xpath = "//a[text()='For Campus']")
	WebElement forCampusBtn;

	//Scrolls and clicks on "For Campus" link
	public ForCampusPage clickForCampusBtn() {
//		executeJS("arguments[0].scrollIntoView()",forCampusBtn);
		elementClick(forCampusBtn);

		ForCampusPage forCampusPage = new ForCampusPage(driver, logger);
		PageFactory.initElements(driver,forCampusPage);
		return forCampusPage;

	}

}
