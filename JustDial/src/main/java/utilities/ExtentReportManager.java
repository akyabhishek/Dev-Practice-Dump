package utilities;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportManager {

	//public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;

	public static ExtentReports getReportInstance() {
		
		if(report ==null) {
			String reportNameString=DateUtils.getTimeStamp()+".html";
			ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"//test-output//"+reportNameString);
			report=new ExtentReports();
			report.attachReporter(htmlReporter);
			report.setSystemInfo("OS", "Windows 11");
			report.setSystemInfo("Build", "10.8.1");
			report.setSystemInfo("Browser", "Chrome");
			
			htmlReporter.config().setDocumentTitle("JustDial Automation Results");
			htmlReporter.config().setReportName("All JustDial UI Test reports");
		}
		return report;
	}

}
