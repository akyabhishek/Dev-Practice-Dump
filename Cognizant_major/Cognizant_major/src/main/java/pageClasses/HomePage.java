package pageClasses;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.BaseClass;

public class HomePage extends BaseClass {

	public ExtentTest logger;

	public HomePage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	@FindBy(id = "O365_UniversalMeContainer")
	WebElement profilePictureBtn;
	
	@FindBy(id = "mectrl_viewAccount")
	WebElement viewAccountLink;
	
	public void clickUserProfile() {
		executeJS("document.getElementById('O365_UniversalMeContainer').click();");;
//		elementClick(profilePictureBtn);
		elementClick(viewAccountLink);
	}

}
