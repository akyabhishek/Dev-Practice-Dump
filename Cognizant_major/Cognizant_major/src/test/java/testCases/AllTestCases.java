package testCases;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import baseClasses.BaseClass;
import pageClasses.ContactSalesPage;
import pageClasses.ForBusinessPage;
import pageClasses.ForCampusPage;
import pageClasses.HomePage;
import pageClasses.LoginPage;
import pageClasses.SearchPage;
import utilities.TestDataProvider;

public class AllTestCases extends BaseClass {

	LoginPage loginPage;
	HomePage homePage;

	@Test(enabled = true, priority = 0)
	public void loginVerify() {
		invokeBrowser(prop.getProperty("browserName"));
		logger = report.createTest("Verify user login details");
		loginPage = openApplication(prop.getProperty("URL"));
		homePage = loginPage.login("2276766@cognizant.com", "@Devil@Abk@121");
	}

	@Test(enabled = true, priority = 1)
	public void userDetailVerify() {
		logger = report.createTest("Verify user details");
		homePage.clickUserProfile();
	}

}
