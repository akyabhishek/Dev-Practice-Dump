package com.selenuim.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class InvokeBrowserDynamically {
	
	WebDriver driver=null;
	
	@Parameters("browser")
	@BeforeMethod
	public void OpenBrowser(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumProject\\drivers\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		
		//To maximize the Browser
		driver.manage().window().maximize();
	}
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}
	
	@Test
	public void verifyFaceBookHomePage(){
		
		String URL = "https://www.facebook.com";
		

				
		driver.get(URL);

		
		//Verify HomePage Tiitle
		String pageTitle = driver.getTitle();
		System.out.println("We get the Title Like :" +pageTitle);
		
		Assert.assertEquals(pageTitle, "Facebook – log in or sign up");
	
		
	}
}
