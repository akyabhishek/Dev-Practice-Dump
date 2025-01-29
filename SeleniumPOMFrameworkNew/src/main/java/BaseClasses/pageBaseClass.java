package BaseClasses;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class pageBaseClass {

	XSSFCell
	public WebDriver driver=null;
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
//				reportFail(e.getMessage());
				System.err.print(e.getMessage());
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

	

		}
		public void openApplication() {
			driver.get("https://www.rediff.com");
			
		}
		public void getTitle(String expected) {
			assertEquals(driver.getTitle(), expected);
			
		}

}
