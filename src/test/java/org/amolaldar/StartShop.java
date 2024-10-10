package org.amolaldar;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.amolaldar.BaseTest.AndroidBaseTest;
import org.amolaldar.pom.android.CartPage;
import org.amolaldar.pom.android.ProductCatlogue;
import org.amolaldar.utils.ListenersTest;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

@Listeners(ListenersTest.class)
public class StartShop extends AndroidBaseTest {

	@BeforeMethod
	public void preSetup() {
		formPage.preSetupActivity();
	}

	@Test(dataProvider = "getData")
	public void letsShop(HashMap<String,String> input) throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();

		formPage.setCountryName(input.get("country"));
		formPage.setFieldName(input.get("name"));
		formPage.setGender();
		ProductCatlogue productCatlogue = formPage.submit();
		// Define the products to be added to the cart
		List<String> productsToAdd = Arrays.asList("Jordan Lift Off", "PG 3");
		productCatlogue.addProductToCart(productsToAdd);
		CartPage cartPage = productCatlogue.goToCart();
		cartPage.addedProductsInCart(productsToAdd);
		List<Object> list = cartPage.sumOfProductPrices();
		Double sum = (Double) list.get(0);
		Double totalDisplayedPrice = (Double) list.get(1);
		AssertJUnit.assertEquals(sum, totalDisplayedPrice);
		String termsCond = cartPage.termsAndCond();
		AssertJUnit.assertEquals(termsCond, "Terms Of Conditions");
		cartPage.completePurchase();
		softAssert.assertAll();

	}

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonData(
				System.getProperty("user.dir") + "/src/main/java/org/amolaldar/testData/formData.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}
