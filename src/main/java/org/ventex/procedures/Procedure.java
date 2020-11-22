package org.ventex.procedures;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Procedure {
	protected WebDriver browser;
	private static final Logger LOGGER = Logger.getLogger(Procedure.class.getName());
	
	public Procedure(WebDriver browser) {
		this.browser = browser;
	}
	
	protected WebElement findElementBySelector(String elementSelector) {
		WebElement element;
		
		try {
			element = browser.findElement(By.cssSelector(elementSelector));
		}catch(org.openqa.selenium.NoSuchElementException e) {
			element = null;
		}
		
		return element;
	}
	
	protected WebElement findElementByXPath(String xpath) {
		WebElement element;
		
		try {
			element = browser.findElement(By.xpath(xpath));
		}catch(org.openqa.selenium.NoSuchElementException e) {
			element = null;
		}
		
		return element;
	}
	
	protected WebElement findElementByName(String name) {
		WebElement element;
		
		try {
			element = browser.findElement(By.name(name));
		}catch(org.openqa.selenium.NoSuchElementException e) {
			element = null;
		}
		
		return element;
	}
	
	protected void clickByXPath(String xpath) {
		boolean pass = false;
		
		while(pass == false) {
			try {
				WebDriverWait wait = new WebDriverWait(browser, 20);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
				pass = true;
			}catch(NoSuchElementException e) {
				pass = false;
				LOGGER.warning("Retrying previous click");
				browser.navigate().refresh();
			}catch(TimeoutException e) {
				pass = false;
				LOGGER.warning("Retrying previous click");
				browser.navigate().refresh();
			}
		}
	}
	
	protected void click(String css) {
		boolean pass = false;
		
		while(pass == false) {
			try {
				WebDriverWait wait = new WebDriverWait(browser, 20);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css))).click();
				pass = true;
			}catch(NoSuchElementException e) {
				pass = false;
				LOGGER.warning("Retrying previous click");
				browser.navigate().refresh();
			}catch(TimeoutException e) {
				pass = false;
				LOGGER.warning("Retrying previous click");
				browser.navigate().refresh();
			}
		}
		
	}
	
	protected void sendKeys(String css, String text) {
		WebDriverWait wait = new WebDriverWait(browser, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css))).sendKeys(text);
	}
	
	public abstract void start();
}
