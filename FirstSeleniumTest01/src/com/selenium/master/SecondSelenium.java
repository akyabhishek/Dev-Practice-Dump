package com.selenium.master;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecondSelenium {
	
	@Test
	public void search(){
		
		String URL = "https://opensource-demo.orangehrmlive.com";
		
		//Open the URL with Selenium
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		
		//Only for Windpws
		//System.setProperty("webdriver.chrome.driver", "c:\\test\\selenium.....\\chromedriver.exe")
		
		WebDriver driver = new ChromeDriver();
		
		//To maximize the Browser
		driver.manage().window().maximize();
				
		driver.get(URL);
		
		//To maximize the Browser
		//driver.manage().window().maximize();
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		//login 
		 driver.findElement(By.name("username")).sendKeys("Admin");
		 driver.findElement(By.name("password")).sendKeys("admin123");
		  driver.findElement(By.xpath("//button[@type='submit']")).click();
		  
		  //check for page title 
		  if(driver.getCurrentUrl().contains("dashboard")) 
				System.out.println("Valid URL");
			else
				System.out.println("Invalid URL");
		 
		    //waits put kiya
		  	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'viewAdminModule')]/span")));
			
			//click on Admin
			driver.findElement(By.xpath("//a[contains(@href,'viewAdminModule')]")).click();
			
			//wait till admin page opens
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Job')]")));
			
			//click on job 
			driver.findElement(By.xpath("//span[contains(text(),'Job')]")).click();
			
			
			//wait till div of job title appears
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Job Titles')]")));
			
			
			//click on Job Title
			driver.findElement(By.xpath("//a[contains(text(),'Job Titles')]")).click();
			
			//print table content
			
			
			
			
			
			
			
			
			
			driver.quit();
			
		  
		
		

			
			
			
//		WebElement username = driver.findElement(By.cssSelector("input[placeholder='Username']"));
//		username.isDisplayed();
//		username.isEnabled();
//		username.sendKeys("Admin");
//		
//		WebElement password = driver.findElement(By.cssSelector("input[placeholder='Password']"));
//		password.isDisplayed();
//		password.isEnabled();
//		password.sendKeys("admin123");
//		
//		WebElement button = driver.findElement(By.cssSelector("button[type='submit']"));
//		button.isDisplayed();
//		button.isEnabled();
//		button.click();
		
		
	
	
	
		
	}

	
	public static void main(String[] args) throws InterruptedException{
		// implementing code
		SecondSelenium ha= new SecondSelenium();
		//ha.driverSetup();
		//ha.OpenUrl();
		ha.search();
		//ha.closeBrowser();
	}

}
