package com.selenium.master2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleMultipleWindow {
	WebDriver driver=null;

	@BeforeMethod
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();	
		
		driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select_multiple");
		//To maximize the Browser
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}	
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}
	public void handleFacebook() {
		driver.get("https://www.facebook.com/");
		String mainPagewindow =driver.getWindowHandle();
		System.out.println(mainPagewindow);
		
		
		driver.findElement(By.linkText("Forgotten password?")).click();
		String forgetaccountPageString=driver.getWindowHandle();
		System.out.println(forgetaccountPageString);
	}
	
	@Test
	public void multiwindow() {
		driver.get("https://www.facebook.com/");
		String mainPagewindow =driver.getWindowHandle();
		System.out.println(mainPagewindow);
		
		
		driver.findElement(By.linkText("Forgotten password?")).click();
		String forgetaccountPageString=driver.getWindowHandle();
		System.out.println(forgetaccountPageString);
		
	}

}
