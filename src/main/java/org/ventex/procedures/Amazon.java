package org.ventex.procedures;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Amazon extends Procedure {
	private static final Logger LOGGER = Logger.getLogger(Amazon.class.getName());
	private final String ADD_TO_CART_SELECTOR = "#add-to-cart-button";
	private final String PROCEED_TO_CHECKOUT_SELECTOR1 = "#attach-sidesheet-checkout-button > span > input";
	private final String PROCEED_TO_CHECKOUT_SELECTOR2 = "#hlb-ptc-btn-native";
	private final String USERNAME_TEXTBOX_SELECTOR = "#ap_email";
	private final String USERNAME_CONTINUE_SELECTOR = "#continue";
	private final String PASSWORD_TEXTBOX_SELECTOR = "#ap_password";
	private final String SIGNIN_BUTTON_SELECTOR = "#signInSubmit";
	private final String PLACE_ORDER_SELECTOR = "#submitOrderButtonId > span > input";
	
	public Amazon(WebDriver browser) {
		super(browser);
	}
	
	@Override
	public void start() {
		try {
			checkPage();
			clickAddToCart();
			proceedToCheckout();
			signin();
			purchase();
			Thread.sleep(15000);
			browser.close();
		} catch (InterruptedException e) {
			LOGGER.severe("Thread sleep error");
		}
	}
	
	private void checkPage() throws InterruptedException {
		WebElement addToCartButton = findElementBySelector(ADD_TO_CART_SELECTOR);
		
		while(addToCartButton == null) {
			LOGGER.info("Checking page for stock");
			browser.navigate().refresh();
			addToCartButton = findElementBySelector(ADD_TO_CART_SELECTOR);
			
			LOGGER.info("No stock found, sleeping for 10 secs");
			Thread.sleep(10000);
		}
	}
	
	private void clickAddToCart() {
		LOGGER.info("Adding to cart...");
		
		click(ADD_TO_CART_SELECTOR);
	}
	
	private void proceedToCheckout() throws InterruptedException {
		LOGGER.info("Proceeding to checkout...");
		Thread.sleep(3000);			//wait for slidein animation
		
		WebElement proceedButton1 = findElementBySelector(PROCEED_TO_CHECKOUT_SELECTOR1);
		WebElement proceedButton2 = findElementBySelector(PROCEED_TO_CHECKOUT_SELECTOR2);
		
		if(proceedButton1 != null) {
			click(PROCEED_TO_CHECKOUT_SELECTOR1);
		}
		else if(proceedButton2 != null) {
			click(PROCEED_TO_CHECKOUT_SELECTOR2);
		}
		else {
			LOGGER.severe("Proceed to checkout button not found.");
		}
	}
	
	private void signin() {
		LOGGER.info("Signing in...");
		
		click(USERNAME_TEXTBOX_SELECTOR);
		sendKeys(USERNAME_TEXTBOX_SELECTOR, System.getenv("amazonUsername"));
		click(USERNAME_CONTINUE_SELECTOR);
		click(PASSWORD_TEXTBOX_SELECTOR);
		sendKeys(PASSWORD_TEXTBOX_SELECTOR, System.getenv("amazonPassword"));
		click(SIGNIN_BUTTON_SELECTOR);
		
		LOGGER.info("Signed in");
	}
	
	private void purchase() {
		LOGGER.info("Placing order...");
		
		click(PLACE_ORDER_SELECTOR);
		
		LOGGER.info("Order placed, shutting down worker.");
	}
}
