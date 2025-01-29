package testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.BaseClass;
import baseClasses.pageBaseClass;
import pageClasses.HomePage;

public class CarWashingServices extends BaseClass {

	HomePage homePage;

	@Test
	public void verifyCarWash() {
		logger = report.createTest("CarWashing Service Provider Test");
		invokeBrowser("Chrome");

		pageBaseClass pageBase = new pageBaseClass(driver, logger);
		PageFactory.initElements(driver, pageBase);
		homePage = pageBase.OpenApplication();
		
		homePage.detectLocationAuto();
		waitLoad(5);
	}

}
