package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.BaseClass;

public class LoginPage extends BaseClass {
	public ExtentTest logger;

	public LoginPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	@FindBy(name = "loginfmt")
	WebElement emailInput;

	@FindBy(name = "passwd")
	WebElement passwordInput;

	@FindBy(id = "idSIButton9")
	WebElement submitBtn;

	@FindBy(xpath = "//a[text()='Not now']")
	WebElement notNowBtn;

	@FindBy(id = "idBtn_Back")
	WebElement noBtn;

	// Login to the Be Cognizant account

	public HomePage login(String email, String password) {
		waitForElementToBeVisible(emailInput, 10);
		enterText(emailInput, email);
		elementClick(submitBtn);
		try {
			waitForElementToBeVisible(passwordInput, 5);
			enterText(passwordInput, password);
			elementClick(submitBtn);
		} catch (Exception e) {
		}
		
		try {
			waitForElementToBeVisible(notNowBtn, 5);
			elementClick(notNowBtn);
		} catch (Exception e) {

		}
		try {
			waitForElementToBeVisible(noBtn, 10);
			elementClick(noBtn);
		} catch (Exception e) {

		}
		
		HomePage homePage = new HomePage(driver, logger);
		PageFactory.initElements(driver, homePage);
		return homePage;
	}

}
