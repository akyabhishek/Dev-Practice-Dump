package com.selenium.master;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class ExtractText {
	@Test
	public void extracttext() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		  WebDriver driver=new ChromeDriver();
		  driver.manage().window().maximize();
		  
		  driver.get("https://www.facebook.com/");
		  
		  String recentTextString=driver.findElement(By.xpath("//*[contains(@class,'_8eso')]")).getText();
		  System.out.println(recentTextString);
		  assertEquals(recentTextString, "Facebook helps you connect and share with the people in your life.");
		  
		  
		  String placeholder=driver.findElement(By.id("email")).getAttribute("placeholder");
		  System.out.println(placeholder);
		  assertEquals(placeholder, "Email address or phone number");
		  
		  driver.quit();
	}

}
