package org.ventex.procedures;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Procedure {
	protected WebDriver browser;
	
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
		WebDriverWait wait = new WebDriverWait(browser, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).click();
	}
	
	protected void click(String css) {
		WebDriverWait wait = new WebDriverWait(browser, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css))).click();
	}
	
	protected void sendKeys(String css, String text) {
		WebDriverWait wait = new WebDriverWait(browser, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css))).sendKeys(text);
	}
	
	public abstract void start();
}
