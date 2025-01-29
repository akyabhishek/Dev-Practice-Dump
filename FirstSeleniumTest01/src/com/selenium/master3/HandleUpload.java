package com.selenium.master3;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleUpload {
	WebDriver driver=null;
	@BeforeMethod
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();	
		
		driver.get("http://the-internet.herokuapp.com/upload");
		//To maximize the Browser
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}	
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}
	@Test
	public void handleUpload() {
		
		
		WebElement uplaodBtn = driver.findElement(By.id("file-upload"));
		String filePath = "C:\\Users\\Abhishek\\Downloads\\FBb77_3XMAEFqyx.jpg";
		
		
		
		uplaodBtn.sendKeys(filePath);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.findElement(By.id("file-submit")).click();
		
		String confirmationTxt = driver.findElement(By.xpath("//*[@id='content']/div/h3")).getText();
		assertEquals(confirmationTxt, "File Uploaded!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
