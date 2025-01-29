package PageClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import BaseClasses.pageBaseClass;

public class LoginPage extends pageBaseClass{

	public LoginPage(WebDriver driver) {
		this.driver=driver;
		
	}
	
	
	public RediffMailPage doLogin() {
		
		return PageFactory.initElements(driver, RediffMailPage.class);
	}
	

}
