package org.ventex;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.ventex.sites.Amazon;

public class Bot implements Runnable {
	private WebDriver chrome;
	private String url;
	private String site;
	private JSONObject config;
	
	public Bot(String url, JSONObject config) {
		this.url = url;
		this.config = config;
		openBrowser();
		determineSite();
	}
	
	//implemented method that runs on a separate thread
	@Override
	public void run() {
		if(site.equalsIgnoreCase("amazon")) {
			new Thread(() -> {
				Amazon amazon = new Amazon(chrome, config.getString("amazonUsername"), config.getString("amazonPassword"));
				amazon.start();
			}).start();
		}
	}
	
	public void openBrowser() {
		chrome = new ChromeDriver();
        chrome.get(this.url);
	}
	
	public void quit() {
		chrome.quit();
	}
	
	private void determineSite() {
		if(url.toLowerCase().contains("amazon")) {
			site = "amazon";
		}
		else{
			site = "site";
		}
	}
}
