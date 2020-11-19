package org.ventex;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Bot {
	WebDriver chrome;
	
	public void setup(String url) {
		openBrowser(url);
	}
	
	public void openBrowser(String url) {
		chrome = new ChromeDriver();
        chrome.get(url);
	}
	
	public void quit() {
		chrome.quit();
	}
}
