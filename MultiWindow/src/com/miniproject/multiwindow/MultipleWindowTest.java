package com.miniproject.multiwindow;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MultipleWindowTest {
	WebDriver driver=null;
	
	@Parameters("browser")
	@BeforeMethod
	public void OpenBrowser(String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver=new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else {
			System.err.println("Invalid browser parameter value");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		//To maximize the Browser
		driver.manage().window().maximize();
	}	
	@AfterMethod
	public void CloseBrowser() {
		driver.quit();
	}
	@Test
	public void handleMultiWindow(){
		
		driver.get("https://www.rediff.com/");
		
		
		//Clicking on "Create Account" link
		driver.findElement(By.xpath("//a[@title='Create Rediffmail Account']")).click();
		
		
		//Validating "Create Rediffmail account" webpage is opened
		assertEquals(driver.getTitle(), "Rediffmail Free Unlimited Storage");
		
		//finding total number of links
		List<WebElement> links= driver.findElements(By.tagName("a"));
		
		System.out.println("Total number of links - "+links.size());
		
		//printing all links
		for(WebElement link:links) {
			System.out.println(""+link.getAttribute("href"));
		}
		
		//clicking on terms and condition link
		driver.findElement(By.xpath("//a[contains(@href,'terms_win()')]")).click();
		
		
		//getting window IDs
		Set<String> windowIDs= driver.getWindowHandles();
		Iterator<String> itr = windowIDs.iterator();
		
		String parentID=itr.next();
		String childID=itr.next();
		
		//Switching to child window
		driver.switchTo().window(childID);
		
		//getting title of child window
		String childTitle=driver.getTitle();
		
		//Validating the title of the child window with the expected title
		assertEquals(childTitle, "Rediffmail: Terms and Conditions");
	
		
		//Closing the child window
		driver.close();
		
		//Switching to the parent window
		driver.switchTo().window(parentID);
		
		//closing browser with CloseBrowser() method with @AfterMethod annotation
	
		
	}

}
