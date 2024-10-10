package org.amolaldar.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumUtils {
	public AppiumDriverLocalService service;
	static AppiumDriver driver;

	public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
		// Start Appium service
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")) // Correct path to
																								// main.js
				.withIPAddress(ipAddress).usingPort(port).build();

		service.start();
		return service;
	}

	public void waitTillEleVisible(WebElement ele, String text, AppiumDriver driver) {
		// Wait until the cart page loads and contains the correct title
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains(ele, "text", text));

	}

	public Double totalFormattedPrice(WebElement ele) {
		// Total price in the cart
		String formattedTotalPrice = ele.getText().substring(1);
		Double price = Double.parseDouble(formattedTotalPrice);
		return price;
	}

	public static String getScreenshotPath(String testcaseName, AppiumDriver driver) throws IOException {
		// Capture screenshot (Assuming WebDriver setup is available)
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/screenshots" + testcaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}

	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {

		// Read the content of the JSON file as a String
	    String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);

	    // Create an ObjectMapper instance for JSON parsing
	    ObjectMapper mapper = new ObjectMapper();

	    // Convert JSON content to List of HashMaps
	    List<HashMap<String, String>> data = mapper.readValue(jsonContent, 
	            new TypeReference<List<HashMap<String, String>>>() {});
	    
	    return data;

	}
}
