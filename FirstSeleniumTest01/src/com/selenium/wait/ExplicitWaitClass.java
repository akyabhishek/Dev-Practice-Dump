package com.selenium.wait;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class ExplicitWaitClass {
	
	WebDriver driver=null;
  
  @BeforeMethod
  public void beforeMethod() {
	  System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
	  driver=new ChromeDriver();
	  driver.manage().window().maximize();
	  
	  driver.get("file:///D:/SRMCEM/nurture/Flexi%20Internship/03%20Selenium%20Automation%20Techniques,%20Dynamic%20XPath/ExplicitWait.html");
	  
  }
  
  public void verifyAler() {
	  WebElement alertBtn=driver.findElement(By.xpath("//*[@id='alert']"));
	  alertBtn.click();
	  
	  WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(30));
	  wait.until(ExpectedConditions.alertIsPresent());
	  
	  driver.switchTo().alert().accept();
	  
	  
  }
 
  public void verifyElementClickable() {
	  driver.findElement(By.id("display-other-button")).click();
	  
	  WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(30));
	  wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("hidden"))));
  }
  public void verifyElementSelected() {
	  driver.findElement(By.id("checkbox")).click();
	  
	  WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(30));
	  wait.until(ExpectedConditions.elementToBeSelected(driver.findElement(By.id("ch"))));
  }
  @Test
  public void verifyCurrentText() {
	  driver.findElement(By.id("populate-text")).click();
	  
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	  wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(By.xpath("//h2[@class='target-text']")), "Selenium Webdriver"));
	  
  }
  
  @AfterTest
  public void closeBrowser() {
	  driver.quit();
  }

 

}
