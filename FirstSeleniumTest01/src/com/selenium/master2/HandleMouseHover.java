package com.selenium.master2;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HandleMouseHover {
	WebDriver driver=null;
	@BeforeMethod
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();	
		
		driver.get("https://mail.rediff.com/cgi-bin/login.cgi");
		//To maximize the Browser
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}	
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}

	@Test
	public void handleMouseHoverOnClick(){
		
		driver.get("https://www.americangolf.co.uk/");
		WebElement clothLink = driver.findElement(By.xpath("//*[@id='header-navigation']/div[1]/ul/li[4]/a"));
		
		Actions action = new Actions(driver);
		action.moveToElement(clothLink).build().perform();
		
		//WebDriverWait wait = new WebDriverWait(driver, 20);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='CLOTHFOOTW_1']/ul/li[3]/ul/li[1]/a/span"))));
		
		WebElement trouserLink = driver.findElement(By.xpath("//*[@id='CLOTHFOOTW_1']/ul/li[3]/ul/li[1]/a/span"));
		trouserLink.click();
		
				
	}

}
