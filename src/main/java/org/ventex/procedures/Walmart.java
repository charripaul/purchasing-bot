package org.ventex.procedures;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Walmart extends Procedure{
	private static final Logger LOGGER = Logger.getLogger(Walmart.class.getName());
	
	private static final String ACCOUNT_BUTTON_SELECTOR = "#hf-account-flyout > span";
	private static final String SIGNOUT_BUTTON_SELECTOR = "#vh-account-menu-root > div.o_a.o_o > div > a:nth-child(4) > div";
	private static final String SIGNIN_BUTTON_SELECTOR = "#vh-account-menu-root > div.o_a.o_o > div > a:nth-child(1)";
	private static final String CLOSE_ACCOUNT_BUTTON_SELECTOR = "#vh-account-menu-root > div.b_a.o_a.o_f.bc_a.b_g.b_t > button > span";
	private static final String EMAIL_TEXTBOX_SELECTOR = "#email";
	private static final String PASSWORD_TEXTBOX_SELECTOR = "#password";
	private static final String CONFIRM_SIGNIN_BUTTON_SELECTOR = "#sign-in-form > button.button.m-margin-top.text-inherit";
	private static final String CAPTCHA_NOTIFICATION = "#sign-in-widget > div.sign-in-widget > div > p";
	private static final String CAPTCHA_NOTIFICATION2 = "#sign-in-form > div.captcha.re-captcha > div > p";
	private static final String HOME_ON_LOGIN_BUTTON_SELECTOR = "#sign-in-widget > a";
	private static final String CART_NUM_SELECTOR = "#header-bubble-links";
	
	public Walmart(WebDriver browser, String username, String password) {
		super(browser, username, password);
	}

	public void signout() {
		openAccountPanel();
		WebElement signoutButton = browser.findElement(By.cssSelector(SIGNOUT_BUTTON_SELECTOR));
		
		if(signoutButton.getText().equalsIgnoreCase("Sign out")) {
			signoutButton.click();
			
			WebElement homeButton = browser.findElement(By.cssSelector(HOME_ON_LOGIN_BUTTON_SELECTOR));
			homeButton.click();
		}
		else {
			closeAccountPanel();
		}
	}
	
	public void signin() {
		openAccountPanel();
		WebElement signinButton = browser.findElement(By.cssSelector(SIGNIN_BUTTON_SELECTOR));
		
		if(signinButton.getText().equalsIgnoreCase("Sign in")) {
			signinButton.click();
		}
		
		WebElement emailTextbox = findElement(EMAIL_TEXTBOX_SELECTOR);
		if(emailTextbox != null) {
			emailTextbox.click();
			emailTextbox.sendKeys(username);
			WebElement passwordTextBox = browser.findElement(By.cssSelector(PASSWORD_TEXTBOX_SELECTOR));
			passwordTextBox.click();
			passwordTextBox.sendKeys(password);
			WebElement confirmSigninButton = browser.findElement(By.cssSelector(CONFIRM_SIGNIN_BUTTON_SELECTOR));
			confirmSigninButton.click();
		}
		
		captchaCheck();
	}
	
	public void captchaCheck() {		
		WebElement captchaText = findElement(CAPTCHA_NOTIFICATION);
		WebElement captchaText2 = findElement(CAPTCHA_NOTIFICATION2);
		
		while((captchaText != null && captchaText.getText().equalsIgnoreCase("Help us keep your account safe by clicking on the checkbox below.")) ||
			  (captchaText2 != null && captchaText2.getText().equalsIgnoreCase("Help us keep your account safe by clicking on the checkbox below."))) {
			LOGGER.info("Awaiting Captcha");
			
			try {
				Thread.sleep(2000);
				captchaText = findElement(CAPTCHA_NOTIFICATION);
			} catch (InterruptedException e) {
				
			}
		}
		
		LOGGER.info("Captcha cleared");
	}
	
	//TODO: fix
	public int getCartValue() {
		WebElement cartValue = browser.findElement(By.cssSelector(CART_NUM_SELECTOR));
		return Integer.parseInt(cartValue.getText());
	}
	
	public void openAccountPanel() {
		WebElement accountButton = browser.findElement(By.cssSelector(ACCOUNT_BUTTON_SELECTOR));
		accountButton.click();
	}
	
	public void closeAccountPanel() {
		WebElement closeAccountButton = browser.findElement(By.cssSelector(CLOSE_ACCOUNT_BUTTON_SELECTOR));
		closeAccountButton.click();
	}
	
	public void guaranteeFreshSession() {
		captchaCheck();
		signout();
		signin();
	}
	
	public void purchase() {
		guaranteeFreshSession();
	}
}
