package org.amolaldar.pom.android;

import org.amolaldar.utils.AndroidActions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage extends AndroidActions {

	private AndroidDriver driver;

	public FormPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
	private WebElement countryDropdown;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	private WebElement fieldName;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	private WebElement femaleOption;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	private WebElement maleOption;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement letsShopBtn;

	@AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
	private WebElement toastMsg;

	public void setCountryName(String countryName) {
		countryDropdown.click();
		scrollToSpecText(countryName);
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + countryName + "\")")).click();

	}

	public void setFieldName(String name) {
		fieldName.sendKeys(name);
		driver.hideKeyboard();
	}

	public void setGender() {
		if (!maleOption.isSelected()) {
			maleOption.click();
		} else
			femaleOption.click();
	}

	public ProductCatlogue submit() {
		letsShopBtn.click();
		return new ProductCatlogue(driver);
		// return toastMsg.getAttribute("name");

	}

	public void preSetupActivity() {
		Activity activity = new Activity("com.androidsample.generalstore",
				"com.androidsample.generalstore.MainActivity");
		// Optionally set other flags like App Wait activity or wait package
		activity.setAppWaitActivity("com.androidsample.generalstore.MainActivity");
		activity.setAppWaitPackage("com.androidsample.generalstore");
		((JavascriptExecutor) driver).executeScript("mobile:startActivity", ImmutableMap.of("Intent",
				"com.androidsample.generalstore/com.androidsample.generalstore.SplashActivity"));

	}

}
