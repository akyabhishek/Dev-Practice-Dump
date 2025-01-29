package PageClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BaseClasses.pageBaseClass;

public class LandingPage extends pageBaseClass{
	
	@FindBy(xpath = "//a[contains(@class,'moneyicon')]");
	public WebElement moneyLink;
	public LoginPage clickSignIn() {
	
		return PageFactory.initElements(driver, LoginPage.class);
	}
	public MoneyPage clickOnMoneyLink() {
		moneyLink.click();
		return PageFactory.initElements(driver, MoneyPage.class);
	}
	//WebElements
	//Operations on Webelements
}
