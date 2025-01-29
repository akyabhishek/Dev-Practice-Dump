package com.selenium.master3;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class manageJavaScriptExecutor {
	WebDriver driver=null;
	@BeforeMethod
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();	
		
		driver.get("https://www.google.com/");
		//To maximize the Browser
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}	
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}
	@Test
	public void jse() {
		JavascriptExecutor jsExecutor= (JavascriptExecutor) driver;
		
		jsExecutor.executeScript("window.location='https://www.rediff.com/'");
		String titleString =(String) jsExecutor.executeScript("return document.title");
		
		assertEquals(titleString, "Rediff.com: News | Rediffmail | Stock Quotes | Shopping");
		
//		WebElement signInBtnElement=  driver.findElement(By.className("signin"));
//		jsExecutor.executeScript("arguments[0].click()", signInBtnElement);
//		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		WebElement username=driver.findElement(By.id("login1"));
//		
//		jsExecutor.executeScript("arguments[0].value='abhishekkumaryadav@gmail.com'", username);
		
		jsExecutor.executeScript("window.scrollBy(0,520)");
		
		WebElement toc =driver.findElement(By.linkText("Terms of use"));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true)", toc);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
