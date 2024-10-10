package org.amolaldar.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {
	static ExtentReports extent;

	public static ExtentReports getReport() {
		String reportPath = System.getProperty("user.dir") + "/Reports/index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("Web Automation Report");
		reporter.config().setDocumentTitle("Test Execution Result");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Amol");
		return extent;

	}
}
