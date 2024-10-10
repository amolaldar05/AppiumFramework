package org.amolaldar;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.amolaldar.BaseTest.AndroidBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.Activity;

public class errorValidationTest extends AndroidBaseTest {

	@BeforeMethod
	public void preSetupActivity() {
		formPage.preSetupActivity();

	}
	@Test
	public void formPositiveTest() {
		formPage.setCountryName("Argentina");
		formPage.setFieldName("Amol Test");
		formPage.setGender();
		formPage.submit();
	}
	
	@Test
	public void formNegativeTest() {
		formPage.setCountryName("Argentina");
		//formPage.setFieldName("Amol Test");
		formPage.setGender();
		formPage.submit();
		AssertJUnit.assertTrue(driver.findElements(By.xpath("(//android.widget.Toast)[1]")).size()<1);
	}
	
	
}
