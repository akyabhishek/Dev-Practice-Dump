package com.selenuim.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstSeleniumTest {
	
	@Test
	public void verifyFaceBookHomePage(){
		
		String URL = "https://www.facebook.com";
		
		//Open the URL with Selenium in Chrome
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\MultiWindow\\drivers\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		
		//Open the URL with Selenium in firefox
//		System.setProperty("webdriver.gecko.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\geckodriver.exe");
//		WebDriver driver = new FirefoxDriver();
		
		//Only for Windows
		//System.setProperty("webdriver.chrome.driver", "c:\\test\\selenium.....\\chromedriver.exe")
		
		
		
		//To maximize the Browser
		driver.manage().window().maximize();
				
		driver.get(URL);
		
		//To maximize the Browser
		driver.manage().window().maximize();
		
		//Verify HomePage Tiitle
		String pageTitle = driver.getTitle();
		System.out.println("We get the Title Like :" +pageTitle);
		
		Assert.assertEquals(pageTitle, "Facebook – log in or sign up");
		
		//To Close the Browser
		driver.close();
		
	}

}
