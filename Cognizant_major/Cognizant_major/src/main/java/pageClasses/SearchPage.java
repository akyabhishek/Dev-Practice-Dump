package pageClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.BaseClass;
import utilities.LocalTextFormatter;

public class SearchPage extends BaseClass {
	public ExtentTest logger;

	public SearchPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	@FindBy(xpath = "//span[contains(@class,'cds-checkboxAndRadio-labelContent') and text()='Beginner']")
	WebElement beginner;

	@FindBy(xpath = "//span[contains(@class,'cds-checkboxAndRadio-labelContent') and text()='Intermediate']")
	WebElement intermediate;

	@FindBy(xpath = "//span[contains(@class,'cds-checkboxAndRadio-labelContent') and text()='Advanced']")
	WebElement advanced;

	@FindBy(xpath = "//span[contains(@class,'cds-checkboxAndRadio-labelContent') and text()='Mixed']")
	WebElement mixed;

	@FindBy(xpath = "//span[contains(@class,'cds-checkboxAndRadio-labelContent') and text()='English']")
	WebElement engLanguagebtn;

	@FindAll(value = { @FindBy(xpath = "//div[@data-e2e='ProductCard']") })
	List<WebElement> productCards;

	@FindAll(value = { @FindBy(xpath = "//div[@data-e2e='product-card-cds']") })
	List<WebElement> productCards2;

	@FindBy(xpath = "//div[@data-e2e='NumberOfResultsSection']/span")
	WebElement numberOfResultElement;

	// filters different level via passed String
	public void filterLevel(String level) {
		if (level.equalsIgnoreCase("Beginner")) {
			elementClick(beginner);
		} else if (level.equalsIgnoreCase("Intermediate")) {
			elementClick(intermediate);
		} else if (level.equalsIgnoreCase("Advanced")) {
			elementClick(advanced);
		} else if (level.equalsIgnoreCase("Mixed")) {
			elementClick(mixed);
		} else {
			reportFail("Enter valid filter type");
		}
	}

	// Selects English as language
	public void selectEnglishLanguage() {
		elementClickViaHover(engLanguagebtn);
	}

	// Prints Name, rating, duration of top two courses from search result
	public void printFirstTwoCourse() {
//		waitForElementToBeVisible(productCards.get(0), 10);
		printTitle("Top Two Courses");
		for (int i = 0; i < 2; i++) {
			String name = null, duration = null, rating = null;
			if (!productCards.isEmpty()) {
				name = productCards.get(i).findElement(By.tagName("h2")).getText();
				duration = productCards.get(i).findElement(By.xpath("//div[2]/div[2]/p")).getText();
				rating = productCards.get(i).findElement(By.xpath("//div[2]/div[2]/div[1]/p[1]")).getText();
			} else {
				name = productCards2.get(i).findElement(By.tagName("h3")).getText();
				rating = productCards2.get(i).findElement(By.xpath("//div[@class='cds-CommonCard-ratings']/div/p[1]"))
						.getText();
				duration = productCards2.get(i).findElement(By.xpath("//div[@class='cds-CommonCard-metadata']/p"))
						.getText();
			}
			duration = LocalTextFormatter.formatDuration(duration);
			printSubTitle("Course " + (i + 1));
			System.out.println("Name - " + name + "\nRatings - " + rating + "\t Duration - " + duration + "\n");
		}
	}

	// Returns number of search result
	public String getNumberOfResult() {
		return LocalTextFormatter.formatSearchResult(numberOfResultElement.getText());
	}

	// Prints name of all the courses from search result
	public void printCourses(String title) {
		try {
			printSubTitle(title);
//		waitForElementToBeVisible(productCards.get(0), 20);
			String name = null;
			if (!productCards.isEmpty()) {
				for (int i = 0; i < productCards.size(); i++) {
					name = productCards.get(i).findElement(By.tagName("h2")).getText();
					System.out.println(i + 1 + ". " + name);
				}
			} else {
				for (int i = 0; i < productCards2.size(); i++) {
					name = productCards2.get(i).findElement(By.tagName("h3")).getText();
					System.out.println(i + 1 + ". " + name);
				}
				
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

}
