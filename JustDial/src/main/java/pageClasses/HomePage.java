package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.pageBaseClass;

public class HomePage extends pageBaseClass{

	public HomePage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}
	
	@FindBy(id="city-auto-sug")
	WebElement locationBox;
	
	@FindBy(id="main-auto")
	WebElement searchBox;
	
	@FindBy(id="react-autowhatever-city-auto-suggest--item-0")
	WebElement detectLocationBtn;
	
	
	@Test
	public void detectLocationAuto() {
		elementClick(locationBox);
		
	}
}
