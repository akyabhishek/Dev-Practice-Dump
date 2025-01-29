package com.selenium.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SleepWaitClass {
	
	@Test
	public void googleSearch() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		
		WebDriver driver= new ChromeDriver();
		
		driver.get("https://www.google.com");
		
		WebElement searchbox= driver.findElement(By.xpath("//input[@name='q' and @title='Search']"));
		searchbox.sendKeys("Selenium-webdriver");
		driver.quit();
	}

}
