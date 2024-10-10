package org.amolaldar.BaseTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.amolaldar.pom.android.FormPage;
import org.amolaldar.utils.AppiumUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AndroidBaseTest extends AppiumUtils {
	public AppiumDriverLocalService service;
	public AndroidDriver driver;
	public FormPage formPage;

	@BeforeClass
	public void configureAppium() throws URISyntaxException, IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				"/Users/catalinalive/eclipse-workspace/AppiumFramework/src/main/java/org/amolaldar/testData/globalConfig.properties");
		prop.load(fis);
		//below line is for checking ipAddress is coming from maven commands or properties file.
		String ipAddress=System.getProperty("ipAddress") != null? System.getProperty("ipAddress"):prop.getProperty("ipAddress");
		//String ipAddress = prop.getProperty("ipAddress");
		String port = prop.getProperty("port");
		String deviceName = prop.getProperty("AndriodDevice");
		// To start Appium server programmatically
		service = startAppiumServer(ipAddress, Integer.parseInt(port));

		// Set up options for AndroidDriver
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(deviceName); // Your device/emulator name
		options.setChromedriverExecutable("//Users//catalinalive//eclipse-workspace//Drivers//chrome-mac-x64"); // Path
																												// to
		// Chromedriver
		options.setApp(
				System.getProperty("user.dir") + "//src//main//java//org//amolaldar//resources//General-Store.apk"); // Path
		// to
		// APK

		// Initialize AndroidDriver
		driver = new AndroidDriver(service.getUrl(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		formPage = new FormPage(driver);

	}

	@AfterClass
	public void tearDown() {
		// Stop the driver after execution
		if (driver != null) {
			driver.quit();
		}

		// Stop the Appium service
		if (service != null) {
			service.stop();
		}
	}

}
