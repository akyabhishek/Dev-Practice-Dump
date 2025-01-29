package com.minipro.alerts;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HandleAlerts {

	WebDriverWait wait = null;
	WebDriver driver = null;

	@Parameters("broswer")
	@BeforeMethod
	public void setup(String broswer) {
		
		//Add equivalent driver.exe file in drivers directory
		
		//Chrome browser handling
		if (broswer.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		//Edge browser handling
		else if (broswer.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		//Firefox browser handling
		else if (broswer.equalsIgnoreCase("Firefox") || broswer.equalsIgnoreCase("Mozilla")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

	}

	@Test()
	public void checkAlerts() {

		driver.manage().window().maximize();
		
		//Implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		// Launching the browser with URL
		driver.get("http://demo.automationtesting.in/Alerts.html");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		
		//hover to switchTo 
		WebElement switchToMenu = driver.findElement(By.xpath("//a[text()='SwitchTo']"));
		Actions moveAction = new Actions(driver);
		moveAction.moveToElement(switchToMenu).build().perform();

		//clicking on Alert menu
		WebElement alertMenuElement = driver.findElement(By.xpath("//a[text()='Alerts']"));
		wait.until(ExpectedConditions.elementToBeClickable(alertMenuElement));
		alertMenuElement.click();

		// Alert with OK
		alertWithOk();

		// Alert with OK and Cancel (Confirm)
		alertWithOkAndCancel();

		// Alert with TextBox (Prompt)
		alertWithTextBox();

	}

	public void alertWithOk() {
		WebElement alertWithOkTab = driver.findElement(By.xpath("//a[text()='Alert with OK ']"));
		alertWithOkTab.click();

		driver.findElement(By.xpath("//div[@id='OKTab']/button")).click();

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		if (alert != null) {
			driver.switchTo().alert().accept();
		} else {
			System.err.print("Alert is not present");
		}
	}

	public void alertWithOkAndCancel() {
		WebElement alertWithCancelTab = driver.findElement(By.xpath("//a[text()='Alert with OK & Cancel ']"));
		alertWithCancelTab.click();
		

		driver.findElement(By.xpath("//div[@id='CancelTab']/button")).click();
		
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Alert confirm = wait.until(ExpectedConditions.alertIsPresent());
		if (confirm != null) {
			driver.switchTo().alert().dismiss();
		} else {
			System.err.print("Alert is not present");
		}
		String response = driver.findElement(By.id("demo")).getText();
		System.out.println(response);

	}

	public void alertWithTextBox() {
		WebElement alertWithTextTab = driver.findElement(By.xpath("//a[text()='Alert with Textbox ']"));
		alertWithTextTab.click();

		driver.findElement(By.xpath("//div[@id='Textbox']/button")).click();
		Alert prompt = wait.until(ExpectedConditions.alertIsPresent());
		if (prompt != null) {
			Alert newal = driver.switchTo().alert();
			newal.sendKeys("Abhishek");
			newal.accept();

		} else {
			System.err.print("Alert is not present");
		}
		String response = driver.findElement(By.id("demo1")).getText();
		System.out.println(response);

	}

	@AfterMethod
	private void closingFunc() {
		driver.close();

	}
}
