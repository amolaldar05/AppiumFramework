package org.amolaldar.utils;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.AppiumDriver;

public class ListenersTest extends AppiumUtils implements ITestListener {
	ExtentReports extent = ExtentReporter.getReport();
	ExtentTest test;
	String testcaseName;
	AppiumDriver driver;

	@Override
	public void onTestStart(ITestResult result) {
		testcaseName = result.getMethod().getMethodName();
		System.out.println("Test Started: " + result.getName());
		test = extent.createTest(testcaseName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Passed: " + result.getName());
		test.log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed: " + result.getName());
		test.fail(result.getThrowable());
		test.log(Status.FAIL, "Test Failed: " + testcaseName);
		// Below code will get a driver here
		try {
			driver = (AppiumDriver) result.getTestClass().getRealClass().getDeclaredField("driver") // Get the field
																									// 'driver'
					.get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// below code will attach a screenshot to the report
		try {
			test.addScreenCaptureFromPath(getScreenshotPath(testcaseName, driver), testcaseName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped: " + result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test Failed but within success percentage: " + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test Execution Started: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test Execution Finished: " + context.getName());
	}

}
