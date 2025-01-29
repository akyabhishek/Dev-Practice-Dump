package TestCases;

import org.testng.annotations.Test;

import PageClass.LandingPage;
import PageClass.LoginPage;
import PageClass.RediffMailPage;

public class MailTests {
	@Test
	public void writeMailTest() {
		
		LandingPage landingPage=new LandingPage();
		landingPage.openBrowser();
		LoginPage loginPage=landingPage.clickSignIn();
		
		RediffMailPage rediffMailPage=loginPage.doLogin();
	}

}
