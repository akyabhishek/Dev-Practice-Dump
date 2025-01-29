package com.selenium.master3;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class handleWebTable {
	WebDriver driver=null;
	@BeforeMethod
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();	
		
		driver.get("https://www.rediff.com");
		//To maximize the Browser
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}	
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}
	@Test
	public void handleWebTab(){
		driver.findElement(By.xpath("//a[contains(@class,'moneyicon')]")).click();
		
		WebDriverWait wait =new WebDriverWait(driver,Duration.ofMinutes(1));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(@href,'indices')]"))));
		
		
		driver.findElement(By.xpath("//a[contains(@href,'indices')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//table[@class='dataTable']"))));
		
		driver.findElement(By.id("showMoreLess")).click();
		
		List<WebElement> allrows=driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr"));
		
		
		
		List<WebElement> allcols=driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[1]/td"));
		
		System.out.println(allrows.size());
		System.out.println(allcols.size());
		
		System.out.println("------Data of first row---------");
		for (WebElement data : allcols) {
			System.out.println(data.getText());
			
		}
		System.out.println("------Data of first column---------");
		List<WebElement> firstcols=driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr/td[1]"));
		for (WebElement data : firstcols) {
			System.out.println(data.getText());
			
		}
		
		System.out.println("------all data---------");
		for (WebElement row : allrows) {
			System.out.println(row.getText());
			
		}
	}

}
