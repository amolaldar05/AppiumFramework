package org.amolaldar.pom.android;

import java.util.ArrayList;
import java.util.List;

import org.amolaldar.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends AndroidActions {

	private AndroidDriver driver;

	public CartPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
	private WebElement cartText;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
	private List<WebElement> cartProducts;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
	private List<WebElement> prodPrices;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement totalPrice;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/termsButton")
	private WebElement termsButton;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/alertTitle")
	private WebElement alertTitle;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
	private WebElement closeBtn;

	@AndroidFindBy(className = "android.widget.CheckBox")
	private WebElement checkbox;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
	private WebElement purchaseBtn;;

	double sum = 0;

	public List<Object> sumOfProductPrices() throws InterruptedException {
		waitTillEleVisible(cartText, "Cart", driver);
		List<Object> list = new ArrayList<Object>();
		// Price summation

		for (WebElement productPrice : prodPrices) {
			String price = productPrice.getText().substring(1);
			System.out.println(price);
			Double formattedPrice = Double.parseDouble(price);
			sum = sum + formattedPrice;
		}
		Thread.sleep(2000);
		System.out.println("Sum of the price of the products=" + sum);
		Double totalDisplayedprice = totalFormattedPrice(totalPrice);
		list.add(sum);
		list.add(totalDisplayedprice);

		return list;

	}

	public void addedProductsInCart(List<String> productsToAdd) {
		// Verify the products added to the cart

		for (String productToAdd : productsToAdd) {
			boolean isProductInCart = cartProducts.stream()
					.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productToAdd));

			Assert.assertTrue(isProductInCart, "Product " + productToAdd + " was not added to the cart.");
		}

	}

	public String termsAndCond() {
		// longPressGestures
		longPressGestures(termsButton);
		String termsCond = alertTitle.getText();
		closeBtn.click();

		return termsCond;
	}

	public void completePurchase() {
		checkbox.click();
		purchaseBtn.click();
	}
}
