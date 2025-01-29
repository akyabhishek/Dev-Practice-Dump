package com.selenium.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FirstSeleniumTest {
	@BeforeTest
	public void verifyFaceBookHomepage() {
		String URL="https://www.facebook.com";
		
		//Open URL with selenium
		System.setProperty("webdriver.chrome.driver","D:\\Development\\FirstSeleniumProject\\drivers\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get(URL);
		
		//to maximize the browser
		
		driver.manage().window().maximize();
		
		//Confirm page title
		String pageTitle=driver.getTitle();
		System.out.print("Page title- "+pageTitle);
		Assert.assertEquals(pageTitle, "Facebook – log in or sign up");
		
		//to close the browser
		driver.close();
		
	}

}
