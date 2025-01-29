package PageClass;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseClasses.pageBaseClass;

public class MoneyPage  extends pageBaseClass{
	
	@FindBy(xpath = "//*[@id='signin_info']/a[1]")
	public WebElement signInLink;
	
	public void clickSignIn() {
		signInLink.click();
	}

}
