package org.ventex.procedures;

import java.util.logging.Logger;

import org.openqa.selenium.By;
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
	
	public Amazon(WebDriver browser, String username, String password) {
		super(browser, username, password);
	}
	
	@Override
	public void start() {
		try {
			checkPage();
			clickAddToCart();
			proceedToCheckout();
			signin();
			purchase();
//			close();
		} catch (InterruptedException e) {
			LOGGER.severe("Thread sleep error");
		}
	}
	
	private void checkPage() throws InterruptedException {
		WebElement addToCartButton = findElement(ADD_TO_CART_SELECTOR);
		
		while(addToCartButton == null) {
			LOGGER.info("Checking page for stock");
			browser.navigate().refresh();
			addToCartButton = findElement(ADD_TO_CART_SELECTOR);
			
			LOGGER.info("No stock found, sleeping for 10 secs");
			Thread.sleep(10000);
		}
	}
	
	private void clickAddToCart() {
		LOGGER.info("Adding to cart...");
		
		WebElement addToCartButton = browser.findElement(By.cssSelector(ADD_TO_CART_SELECTOR));
		addToCartButton.click();
	}
	
	private void proceedToCheckout() throws InterruptedException {
		LOGGER.info("Proceeding to checkout...");
		Thread.sleep(3000);			//wait for slidein animation
		
		WebElement proceedButton1 = findElement(PROCEED_TO_CHECKOUT_SELECTOR1);
		WebElement proceedButton2 = findElement(PROCEED_TO_CHECKOUT_SELECTOR2);
		
		if(proceedButton1 != null) {
			proceedButton1.click();
		}
		else if(proceedButton2 != null) {
			proceedButton2.click();
		}
		else {
			LOGGER.severe("Proceed to checkout button not found.");
		}
	}
	
	private void signin() {
		LOGGER.info("Signing in...");
		
		WebElement usernameTextbox = browser.findElement(By.cssSelector(USERNAME_TEXTBOX_SELECTOR));
		usernameTextbox.click();
		usernameTextbox.sendKeys(username);
		
		WebElement usernameContinueButton = browser.findElement(By.cssSelector(USERNAME_CONTINUE_SELECTOR));
		usernameContinueButton.click();
		
		WebElement passwordTextbox = browser.findElement(By.cssSelector(PASSWORD_TEXTBOX_SELECTOR));
		passwordTextbox.click();
		passwordTextbox.sendKeys(password);
		
		WebElement signinButton = browser.findElement(By.cssSelector(SIGNIN_BUTTON_SELECTOR));
		signinButton.click();
	}
	
	private void purchase() {
		LOGGER.info("Placing order...");
		
		WebElement placeOrderButton = browser.findElement(By.cssSelector(PLACE_ORDER_SELECTOR));
		placeOrderButton.click();
		
		LOGGER.info("Order placed, shutting down worker.");
	}
}
