package org.ventex;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.ventex.sites.Amazon;

public class Bot {
	private static final Logger LOGGER = Logger.getLogger(Bot.class.getName());
	private WebDriver chrome;
	private String url;
	private String siteName;
	private Map<String, Object> config;
	
	public Bot(String url, Map<String, Object> config) {
		this.url = url;
		this.config = config;
		openBrowser();
		determineSite();
	}
	
	public void run() {
		Thread thread = null;
		
		if(siteName.equalsIgnoreCase("amazon")) {
			thread = new Thread(() -> {
				Amazon amazon = new Amazon(chrome, (String) config.get("amazonUsername"), (String) config.get("amazonPassword"));	
				amazon.start();
			});
		}
		
		if(thread != null) {
			thread.start();
		}
		else {
			LOGGER.severe("Website not recognized or not supported.\nURL: " + url);
		}
		
	}
	
	public void openBrowser() {
		chrome = new ChromeDriver();
        chrome.get(this.url);
	}
	
	private void determineSite() {
		if(url.toLowerCase().contains("amazon")) {
			siteName = "amazon";
		}
		else{
			siteName = "site";
		}
	}
}
