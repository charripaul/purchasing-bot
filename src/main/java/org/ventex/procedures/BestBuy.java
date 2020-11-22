package org.ventex.procedures;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BestBuy extends Procedure{
	private static final Logger LOGGER = Logger.getLogger(BestBuy.class.getName());
	private final String ADD_TO_CART_XPATH = "//*[contains(text(), 'Add to Cart')]";
	private final String GO_TO_CART_XPATH = "//*[contains(text(), 'Go to Cart')]";
	private final String CHECKOUT_SELECTOR = "#cartApp > div.page-spinner.page-spinner--out > div.large-view > div > div > span > div > div.fluid-large-view > div.fluid-large-view__upper-container > section.fluid-large-view__sidebar > div > div > div.order-summary__checkout-buttons-container > div > div.checkout-buttons__checkout > button";
	private final String USERNAME_TEXTBOX_SELECTOR = "#fld-e";
	private final String PASSWORD_TEXTBOX_SELECTOR = "#fld-p1";
	private final String SIGN_IN_SELECTOR = "body > div.cia-app-container > div > section > main > div.cia-content.js-cia-content > div > div > div > div > form > div.cia-form__controls > button";
	private final String SEC_CODE_SELECTOR = "#credit-card-cvv";
	private final String PLACE_ORDER_XPATH = "//*[contains(text(), 'Place Your Order')]";
	
	public BestBuy(WebDriver browser) {
		super(browser);
	}
	
	public void start() {
		try {
			checkPage();
			addToCart();
			goToCart();
			checkout();
			signin();
			purchase();
			browser.close();
		} catch (InterruptedException e) {
			LOGGER.info("Thread sleep error");
		}
		
	}
	
	public void checkPage() throws InterruptedException {
		WebElement addToCartButton = findElementByXPath(ADD_TO_CART_XPATH);
		
		while(addToCartButton == null) {
			LOGGER.info("Checking page for stock");
			browser.navigate().refresh();
			addToCartButton = findElementByXPath(ADD_TO_CART_XPATH);
			
			LOGGER.info("No stock found, sleeping for 20 secs");
			Thread.sleep(20000);
		}
	}
	
	public void addToCart() {
		LOGGER.info("Adding to cart...");
		
		clickByXPath(ADD_TO_CART_XPATH);
	}
	
	public void goToCart() {
		LOGGER.info("Going to cart...");
		
		clickByXPath(GO_TO_CART_XPATH);
	}
	
	public void checkout() {
		LOGGER.info("Checking out...");
		
		click(CHECKOUT_SELECTOR);
	}
	
	public void signin() {
		LOGGER.info("Signing in...");
		
		click(USERNAME_TEXTBOX_SELECTOR);
		sendKeys(USERNAME_TEXTBOX_SELECTOR, System.getenv("bestbuyUsername"));
		click(PASSWORD_TEXTBOX_SELECTOR);
		sendKeys(PASSWORD_TEXTBOX_SELECTOR, System.getenv("bestbuyPassword"));
		click(SIGN_IN_SELECTOR);
	}
	
	public void purchase() {
		LOGGER.info("Placing Order...");
		
		click(SEC_CODE_SELECTOR);
		sendKeys(SEC_CODE_SELECTOR, System.getenv("bestbuySec"));
		clickByXPath(PLACE_ORDER_XPATH);
		
		WebElement placeOrderButton = findElementByXPath(PLACE_ORDER_XPATH);
		if(placeOrderButton != null) {
			System.out.println("place order button found");
			
		}
		else {
			System.out.println("not found");
		}
		
		
		LOGGER.info("Order placed, shutting down worker.");
	}
}
