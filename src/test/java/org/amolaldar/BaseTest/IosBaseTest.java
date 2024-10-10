package org.amolaldar.BaseTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.amolaldar.utils.AppiumUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class IosBaseTest extends AppiumUtils {

	public AppiumDriverLocalService service;
	public IOSDriver driver;

	@BeforeClass
	public void configureAppium() throws URISyntaxException, IOException {
		Properties prop= new Properties();
		FileInputStream fis= new FileInputStream(System.getProperty("User.dir")+"/src/main/java/org/amolaldar/testData/globalConfig.properties");
		prop.load(fis);
		String ipAddress=prop.getProperty("ipAddress");
		String port=prop.getProperty("port");
		// To start Appium server programmatically
		service = startAppiumServer(ipAddress,Integer.parseInt(port));

		// Set up options for AndroidDriver
		XCUITestOptions options = new XCUITestOptions();
		options.setDeviceName("iPhone 16 Pro Max");
		options.setApp("/Users/catalinalive/eclipse-workspace/Appium/src/main/resources/UIKitCatalog.app");
		options.setPlatformVersion("18.0");
		options.setWdaLaunchTimeout(Duration.ofSeconds(10));

		// Initialize IOSDriver
		driver = new IOSDriver(service.getUrl(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@AfterClass
	public void tearDown() {
		// Quit the driver
		driver.quit();

		// Stop the Appium service after the test
		service.stop();
	}

}
