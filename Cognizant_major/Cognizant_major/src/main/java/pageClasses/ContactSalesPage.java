package pageClasses;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import baseClasses.BaseClass;

public class ContactSalesPage extends BaseClass {
	public ExtentTest logger;

	public ContactSalesPage(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	@FindBy(id = "FirstName")
	WebElement firstNameInput;

	@FindBy(id = "LastName")
	WebElement lastNameInput;

	@FindBy(id = "Title")
	WebElement titleInput;

	@FindBy(id = "Primary_Discipline__c")
	WebElement selectDiscipline;

	@FindBy(id = "Email")
	WebElement emailInput;

	@FindBy(id = "Phone")
	WebElement phoneInput;

	@FindBy(id = "Company")
	WebElement companyInput;

	@FindBy(id = "Institution_Type__c")
	WebElement selectInstitutionType;

	@FindBy(id = "Country")
	WebElement selectCountry;

	@FindBy(id = "State")
	WebElement selectState;

	@FindBy(id = "What_the_lead_asked_for_on_the_website__c")
	WebElement selectNeeds;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement submitBtn;

	@FindBy(id = "ValidMsgEmail")
	WebElement errorMessagElement;

	public void setFirstName(String firstname) {
		waitForElementToBeVisible(firstNameInput, 10);
		enterText(firstNameInput, firstname);
	}

	public void setLastName(String lastName) {
		enterText(lastNameInput, lastName);
	}

	public void setTitle(String title) {
		enterText(titleInput, title);
	}

	public void setDiscipline(String discipline) {
		selectOption(selectDiscipline, discipline);
	}

	public void setEmail(String email) {
		enterText(emailInput, email);
	}

	public void setPhone(String phone) {
		enterText(phoneInput, phone);
	}

	public void setCompany(String company) {
		enterText(companyInput, company);
	}

	public void setInstitution(String institution) {
		selectOption(selectInstitutionType, institution);
	}

	public void setCountry(String country) {
		selectOption(selectCountry, country);
	}

	public void setState(String state) {
		waitForElementToBeVisible(selectState, 10);
		selectOption(selectState, state);
	}

	public void setNeeds(String needs) {
		selectOption(selectNeeds, needs);
	}

	public void clickSubmit() {
		elementClickViaHover(submitBtn);
	}

	public String getErrorMsg() {
		return errorMessagElement.getText();
	}

	public void submitForm(String firstname, String lastname, String title, String discipline, String email,
			String phone, String company, String institution, String country, String state, String needs,
			String nextPageTitle, String testType) {
		try {
			setFirstName(firstname);
			setLastName(lastname);
			setTitle(title);
			setDiscipline(discipline);
			setEmail(email);
			setPhone(phone);
			setCompany(company);
			setInstitution(institution);
			setCountry(country);
			setState(state);
			setNeeds(needs);
			clickSubmit();
			if (testType.equals("Invalid")) {
				System.out.println(getErrorMsg());
				reportInfo("Error message while submitting form - " + getErrorMsg());
				takeScreenShotOnFailure();
			}
			waitLoad(5);
			try {
				verifyTitle(nextPageTitle);
			} catch (AssertionError e) {
				verifyTitle("(1) New Messages!");
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}
