package com.liveproject;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class CreatePortfolio {
	WebDriver driver=null;

	@BeforeSuite
	public void OpenBrowser() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		driver=new ChromeDriver();	
		
		//To maximize the Browser
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}	
//	@AfterMethod
//	public void CloseBrowser() {
//		driver.quit();
//	}
	@Test(priority = 1)
	public void testCreatePortfolio() {
		// Open the Refiff
		driver.get("https://www.rediff.com/");
		// Click the Money Link
		driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/a[2]")).click();

		driver.findElement(By.xpath("//*[@id='signin_info']/a[1]")).click();

		driver.findElement(By.xpath("//*[@id='useremail']")).sendKeys("anshulc55@rediffmail.com");
		//driver.findElement(By.xpath("//*[@id='emailsubmit']")).click();

		//WebDriverWait wait = new WebDriverWait(driver, 30);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(180));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='userpass']"))));

		driver.findElement(By.xpath("//*[@id='userpass']")).sendKeys("Test@1234");
		driver.findElement(By.xpath("//*[@id='userpass']")).sendKeys(Keys.ENTER);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("createPortfolio")));
		driver.findElement(By.id("createPortfolio")).click();

		driver.findElement(By.id("create")).clear();
		driver.findElement(By.id("create")).sendKeys("AbhisheksPortfolio");

		driver.findElement(By.id("createPortfolioButton")).click();

	}
	@Test (priority = 2)
	public void testportfolioverification(){
		WebElement portfolioDropdown = driver.findElement(By.id("portfolioid"));
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Select select = new Select(portfolioDropdown);
		String portfolioName = select.getFirstSelectedOption().getText();
		
		assertEquals(portfolioName, "AbhisheksPortfolio");
	}
	

}
