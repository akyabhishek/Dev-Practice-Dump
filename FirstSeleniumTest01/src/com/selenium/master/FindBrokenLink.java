package com.selenium.master;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class FindBrokenLink {

	@Test
	public void findLinks() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumTest01\\drivers\\chromedriver.exe");
		  WebDriver driver=new ChromeDriver();
		  driver.manage().window().maximize();
		  driver.get("https://edition.cnn.com/");
		  
		  List<WebElement> links= driver.findElements(By.tagName("a"));
		  
		  System.out.println("Number of links -"+links.size());
		  for(WebElement link:links) {
			  String url=link.getAttribute("href");
			  GetLinkStatusCode.verifyLink(url);
		  }
		  GetLinkStatusCode.invalidLinkCount();
		  
		  driver.quit();
	}
}
