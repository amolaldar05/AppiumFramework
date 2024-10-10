package org.amolaldar.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class IOSActions extends AppiumUtils {

	private IOSDriver driver;

	protected IOSActions(IOSDriver driver) {
		this.driver = driver;
	}

	public void longPressGestures(WebElement element) {

		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
	}

	public void scrollUptoEndAction() {
		// Java scroll upto end
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent", 1.0));
		} while (canScrollMore);
	}

	// To scroll to a specific text using UIAutomator in Appium with Google’s engine
	public void scrollToSpecText(String text) {
		((SearchContext) driver).findElement(AppiumBy.androidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + text + "\"));"));

	}

	public void swipeGesture(WebElement ele, String dir) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(), "direction", dir, "percent", 0.75));
	}

	public void waitTillEleVisible(WebElement ele, String text) {
		// Wait until the cart page loads and contains the correct title
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains(ele, "text", text));

	}
}
