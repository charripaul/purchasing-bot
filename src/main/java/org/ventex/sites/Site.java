package org.ventex.sites;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Site {
	protected WebDriver browser;
	protected String username;
	protected String password;
	
	public Site(WebDriver browser, String username, String password) {
		this.browser = browser;
		this.username = username;
		this.password = password;
	}
	
	protected WebElement findElement(String elementSelector) {
		WebElement element;
		
		try {
			element = browser.findElement(By.cssSelector(elementSelector));
		}catch(org.openqa.selenium.NoSuchElementException e) {
			element = null;
		}
		
		return element;
	}
}
