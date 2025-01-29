package testCases;

import org.testng.annotations.Test;

import baseClasses.BaseClass;

public class TestSetup extends BaseClass {

	@Test
	public void testSetup() {
		try {
			invokeProperties();
			invokeBrowser(prop.getProperty("browserName"));
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}
