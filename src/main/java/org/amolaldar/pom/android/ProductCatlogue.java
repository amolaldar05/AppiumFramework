package org.amolaldar.pom.android;

import java.util.Iterator;
import java.util.List;

import org.amolaldar.utils.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductCatlogue extends AndroidActions {

	private AndroidDriver driver;

	public ProductCatlogue(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
	private List<WebElement> products;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
	private List<WebElement> addToCartLinks;

	@AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
	private WebElement cartBtn;

	public void addProductToCart(List<String> productsToAdd) throws InterruptedException {

		Iterator<String> it = productsToAdd.iterator();
		while (it.hasNext()) {
			String productName = it.next();
			scrollToSpecText(productName);
			System.out.println(products.size());
			// Use Streams to find the specific product and add it to the cart
			products.stream().filter(product -> product.getText().equalsIgnoreCase(productName)).findFirst()
					.ifPresent(product -> {
						// Find index of the product in the refreshed product list
						int index = products.indexOf(product);
						addToCartLinks.get(index).click();
						System.out.println(productName + " added to cart.");

					});
		}

		/*
		 * // Iterate over each product and add it to the cart for (String productToAdd
		 * : productsToAdd) { // Scroll to the specific product
		 * scrollToSpecText(productToAdd); System.out.println(products.size()); // Use
		 * Streams to find the specific product and add it to the cart
		 * products.stream().filter(product ->
		 * product.getText().equalsIgnoreCase(productToAdd)).findFirst()
		 * .ifPresent(product -> { // Find index of the product in the refreshed product
		 * list int index = products.indexOf(product);
		 * addToCartLinks.get(index).click(); System.out.println(productToAdd +
		 * " added to cart."); });
		 * 
		 * // Optional: pause to ensure the element is clicked and the UI is updated
		 * before // proceeding Thread.sleep(1000); // Adjust sleep time as needed
		 * 
		 * }
		 */

	}

	public CartPage goToCart() {
		cartBtn.click();
		return new CartPage(driver);
	}

}
