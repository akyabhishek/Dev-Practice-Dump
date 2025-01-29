package baseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pageClasses.HomePage;
import pageClasses.LoginPage;
import utilities.DateUtils;
import utilities.ExtentReportManager;

public class BaseClass {

	public WebDriver driver;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public static ExtentTest logger;
	public Properties prop;
	
	/****************** Invoke Properties file ***********************/
	@BeforeClass
	public void invokeProperties() {
		if (prop == null) {
			prop = new Properties();

			try {
				FileInputStream file = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\config\\projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				reportFail(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/****************** Invoke Browser ***********************/
	public void invokeBrowser(String browserName) {
		
		try {

			if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--log-level=3");
				driver = new ChromeDriver(options);
			} else if (browserName.equalsIgnoreCase("Mozila")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe");
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--log-level=3");
				driver = new FirefoxDriver(options);
			} else if (browserName.equalsIgnoreCase("Edge")) {
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\msedgedriver.exe");
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--log-level=3");
				driver = new EdgeDriver(options);
			} else {
				reportFail(browserName + " is not a valid browser name");
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("implicitlyWait"))));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(prop.getProperty("implicitlyWait"))));
		driver.manage().window().maximize();
		
	}

	/****************** Close current tab of browser ***********************/
	public void tearDown() {
		driver.close();
	}

	/****************** Opens URL provided ***********************/
	public void openURL(String url) {
		try {
			driver.get(url);
			reportPass(url + " Identified successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Quits the  Browser ***********************/
//	@AfterClass
	public void quitBrowser() {
		driver.quit();
	}
//	@AfterSuite
	public void flushReport() {
		report.flush();
	}

	/****************** Open Application ***********************/

	public LoginPage openApplication(String URL) {
		try {
			reportInfo("Opening the Website");
			driver.get(URL);
			reportPass("Successfully Opened the " + URL);
			LoginPage loginPage = new LoginPage(driver, logger);
			PageFactory.initElements(driver, loginPage);
			return loginPage;

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return null;
	}

	/****************** Waiting functions ***********************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 60) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 60) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void waitForElementToBeVisible(WebElement element,int duration) {
		new WebDriverWait(driver, Duration.ofSeconds(duration)).until(ExpectedConditions.visibilityOf(element));
	}
	/****************** Reporting Functions ***********************/
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.fail(reportString);
	}

	public void reporSofttFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
	}

	public void reportInfo(String reportString) {
		logger.log(Status.INFO, reportString);
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	/****************** Capture Screenshot ***********************/
	public void takeScreenShotOnFailure() {
		reportInfo("Capturing screenshot");
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "/ScreenShots/" + DateUtils.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "/ScreenShots/" + DateUtils.getTimeStamp() + ".png");
			reportPass("Screenshot caputred and attached in report");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/****************** Get Page Title ***********************/
	public void verifyTitle(String expectedTitle) {
		try {
			reportInfo("Verifying title - "+expectedTitle);
			Assert.assertEquals(driver.getTitle(), expectedTitle);
			reportPass("Actual Title : " + driver.getTitle() + " - equals to Expected Title : " + expectedTitle);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/****************** Enters text in element ***********************/
	public void enterText(WebElement element, String data) {
		try {
			reportInfo("Entering data -"+data + " in locator Element :" + element);
			element.sendKeys(data);
			reportPass(data + " - entered successfully in locator Element :" + element);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Click on any element ***********************/
	public void elementClick(WebElement element) {
		try {
			reportInfo(" Clicking element -"+element);
			element.click();
			reportPass(element + " - element clicked successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Click on any element via hover ***********************/

	public void elementClickViaHover(WebElement element) {
		try {
			reportInfo(" Clicking element via hover -"+element);
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();
			reportPass(element + " - Element clicked via hover mouse action successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Verify Element is Present ***********************/
	public void veriyElementIsDisplayed(WebElement webElement) {
		try {
			if (webElement.isDisplayed()) {
				reportPass("Element is Displayed");
			} else {
				reportFail("Element is not appeared");
			}

		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/****************** Clear the input field ***********************/
	public void clearInput(WebElement element) {
		try {
			reportInfo("Clearing the value of element - " + element);
			String s1 = Keys.chord(Keys.CONTROL, "a");
			element.sendKeys(s1);
			element.sendKeys(Keys.DELETE);
			reportPass("Cleared the value of element - " + element);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Click enter input field ***********************/
	public void clickEnter(WebElement element) {
		try {
		reportInfo("Clicking ENTER key in element - " + element);
		element.sendKeys(Keys.ENTER);
		reportPass("Clicked ENTER key in element - " + element);
		}
		catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Select Option ***********************/
	public void selectOption(WebElement element, String option) {
		try {
			reportPass("Selecting option -" + option + "in select element -" + element);
			Select select = new Select(element);
			select.selectByVisibleText(option);
			reportPass("Selected option -" + option + "in select element -" + element);
		} catch (Exception e) {

			reportFail(e.getMessage());
		}
	}

	/****************** Console print functions ***********************/
	public void printTitle(String text) {
		System.out.println("============================================================");
		System.out.println("\t\t\t" + text);
		System.out.println("============================================================\n");
	}

	public void printSubTitle(String text) {
		System.out.println("************************* " + text + " *************************\n");
	}

	/****************** JavaScript Executor ***********************/
	public void executeJS(String script) {
		try {
			reportInfo("Executing javascript -" + script);
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript(script);
			reportPass("Executed javascript -" + script);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void executeJS(String script, WebElement element) {
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript(script, element);
			reportPass("Executed javascript -" + script + " with element - " + element);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}


}
