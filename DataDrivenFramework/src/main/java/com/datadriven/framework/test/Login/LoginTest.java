package com.datadriven.framework.test.Login;

import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.base.BaseUI;
import com.datadriven.framework.utils.ExtentReportManager;

public class LoginTest extends BaseUI {
	
	
	@Test
	public void testOne() {
		
		
		logger=reports.createTest("Reddif test case");
		invokeBrowser("Chrome");
		openURL("websiteURL");
		elementClick("signinButton_xpath");
		enterText("usernameTextbox_id", "akyabhishek");

	}
	@AfterTest
	public void flushRep() {
		reports.flush();
	}

}
