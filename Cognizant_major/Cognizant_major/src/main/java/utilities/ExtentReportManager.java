package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportManager {

	public static ExtentReports report;

	// Creates instance for ExtentReports
	public static ExtentReports getReportInstance() {

		if (report == null) {
			String reportNameString = DateUtils.getTimeStamp() + ".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "//test-reports//" + reportNameString);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			report.setSystemInfo("OS", "Windows 11");
			report.setSystemInfo("Browser", "Chrome");

			htmlReporter.config().setDocumentTitle("Be Cognizant Automation Results");
			htmlReporter.config().setReportName("All Be Cognizant UI Test reports");
		}
		return report;
	}

}
