package com.datadriven.framework.base;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.utils.DateUtils;
import com.datadriven.framework.utils.ExtentReportManager;

import net.bytebuddy.utility.privilege.GetMethodAction;

public class BaseUI {
	public WebDriver driver;
	public Properties properties;
	public ExtentReports reports = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	SoftAssert softAssert = new SoftAssert();

	// to invoke the browser
	public void invokeBrowser(String broswerName) {
		try {
			if (broswerName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
			} else {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

		if (properties == null) {
			properties = new Properties();
			try {
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "//src//test//resources//ObjectRepository//projectConfig.properties");
				properties.load(file);
			} catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	// opens url
	public void openURL(String websiteURLKey) {
		try {
			driver.get(properties.getProperty(websiteURLKey));
			reportPass(websiteURLKey + " Identified successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// closes the browser
	public void tearDown() {
		driver.close();

	}

	// quits the browser
	public void quitBrowser() {
		driver.quit();
	}

	// enters text in element
	public void enterText(String locatorKey, String data) {
		try {
			getElement(locatorKey).sendKeys(data);
			reportPass(data + " -Entered successfully in locator Element :" + locatorKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// click on any element
	public void elementClick(String locatorKey) {
		try {
			getElement(locatorKey).click();
			reportPass(locatorKey + " :Element clicked successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// verify element displayed
	public Boolean isElementPresent(String locatorKey) {
		try {

			if (getElement(locatorKey).isDisplayed()) {
				reportPass(locatorKey + ": Element is displayed");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	// verify element selected
	public Boolean isElementSelected(String locatorKey) {
		try {

			if (getElement(locatorKey).isSelected()) {
				reportPass(locatorKey + ": Element is selected");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	// verify element enabled
	public Boolean isElementEnabled(String locatorKey) {
		try {

			if (getElement(locatorKey).isEnabled()) {
				reportPass(locatorKey + ": Element is enabled");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	// To identify the page element
	public WebElement getElement(String locatorKey) {

		WebElement element = null;
		try {

			if (locatorKey.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(properties.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator identified " + locatorKey);
			} else if (locatorKey.endsWith("_id")) {
				element = driver.findElement(By.id(properties.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator identified " + locatorKey);
			} else if (locatorKey.endsWith("_css")) {
				element = driver.findElement(By.cssSelector(properties.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator identified " + locatorKey);
			} else if (locatorKey.endsWith("_linkText")) {
				element = driver.findElement(By.linkText(properties.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator identified " + locatorKey);
			} else if (locatorKey.endsWith("_partialLinkText")) {
				element = driver.findElement(By.partialLinkText(properties.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator identified " + locatorKey);
			} else if (locatorKey.endsWith("_name")) {
				element = driver.findElement(By.name(properties.getProperty(locatorKey)));
				logger.log(Status.INFO, "Locator identified " + locatorKey);
			} else {
				reportFail("Failing the TestCase, Invalid locator key " + locatorKey);
			}
		} catch (Exception e) {
			// TODO: handle exception
			reportFail(e.getMessage());
			e.printStackTrace();

			fail("Failing the TestCase" + e.getMessage());

		}
		return element;

	}

	// Assertion functions
	public void assertTrue(boolean flag) {
		softAssert.assertTrue(flag);
	}

	public void assertFlase(boolean flag) {
		softAssert.assertFalse(flag);
	}

	public void assertEquals(String actual, String expected) {
		softAssert.assertEquals(actual, expected);
	}

	@AfterMethod
	public void afterTest() {
		softAssert.assertAll();		driver.quit();
	}

	/********************** Reporting functions *********************/
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	/********************** Takes screenshot *********************/
	public void takeScreenShotOnFailure() {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir") + "//Screenshots//" + DateUtils.getTimeStamp() + ".png";
		File destPath = new File(filePath);
		try {
			FileUtils.copyFile(sourceFile, destPath);
			logger.addScreenCaptureFromPath(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
