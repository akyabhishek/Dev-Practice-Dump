package com.selenuim.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class NewTest {
  @Test
  public void f() {
	  System.out.println("Hello 01");
	  System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
//	  System.out.println("Hello 03");
//		
		WebDriver driver= new ChromeDriver();
//		
		driver.get("https://www.google.com");
//		
		WebElement searchbox= driver.findElement(By.xpath("//input[@name='q' and @title='Search']"));
		searchbox.sendKeys("Selenium-webdriver");
  }
}
