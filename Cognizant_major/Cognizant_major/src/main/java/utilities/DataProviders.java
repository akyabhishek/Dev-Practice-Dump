package utilities;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviders {

	@DataProvider
	public Object[][] getContactFormData(){
		return TestDataProvider.getTestData("CourseraData.xlsx", "CourseraTests", "campusContact");
	}
	@DataProvider
	public Object[][] getLanguageLearningData(){
		return TestDataProvider.getTestData("CourseraData.xlsx", "CourseraTests", "languageLearning");
	}
}
