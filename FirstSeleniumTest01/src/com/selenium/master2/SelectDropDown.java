package com.selenium.master2;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SelectDropDown {
	
	WebDriver driver=null;

	@BeforeMethod
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();	
		
		driver.get("https://www.facebook.com/r.php?locale=en_GB&display=page");
		//To maximize the Browser
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}	
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}
	@Test
	public void selectDob() {
		WebElement monthElement= driver.findElement(By.name("birthday_month"));
		Select select=new Select(monthElement);
		
		select.selectByIndex(5);
	
		
		select.selectByValue("7");
		
		select.selectByVisibleText("Dec");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WebElement> monthValues =select.getOptions();
		for (WebElement month : monthValues) {
			System.out.println(month.getText());
		}
	}
	

}
