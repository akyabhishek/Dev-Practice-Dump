package com.selenuim.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class OptionClassSelenuim {

	@Test
	public void sampleOptionBinary() {
		System.setProperty("webdriver.chrome.driver", "D:\\Development\\FirstSeleniumProject\\drivers\\chromedriver.exe");
	
		WebDriver driver = new ChromeDriver();
		driver.get("https://google.com");
	}
}
