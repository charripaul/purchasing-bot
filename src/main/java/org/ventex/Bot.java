package org.ventex;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.ventex.sites.Walmart;

public class Bot {
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
	
	public void start() {
		if(site.equalsIgnoreCase("walmart")) {
			Walmart walmart = new Walmart(chrome, config.getString("walmartUsername"), config.getString("walmartPassword"));
			walmart.guaranteeFreshSession();
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
		if(url.toLowerCase().contains("walmart")) {
			site = "walmart";
		}
		else{
			site = "site";
		}
	}
}
