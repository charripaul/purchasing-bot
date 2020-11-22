package org.ventex;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.ventex.procedures.Amazon;
import org.ventex.procedures.BestBuy;
import org.ventex.procedures.Procedure;

public class Bot implements Runnable{
	private static final Logger LOGGER = Logger.getLogger(Bot.class.getName());
	private static ChromeOptions options;
	private int id;
	private String url;
	private ChromeDriver chrome;
	private Procedure procedure;
	private String procedureName;
	private Thread thread;
	
	public Bot(int id, String url, String procName) {
		this.id = id;
		this.url = url;
		this.procedureName = procName;
		options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("window-sized1200,600");
		options.addArguments("--proxy-server=*");
		options.addArguments("--proxy-bypass-list=*");
		options.addArguments( "--disable-dev-shm-usage");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-proxy-server");
		options.addArguments("--no-sandbox");
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		LOGGER.info("Starting Bot-" + id);
		openBrowser(url);
		setProcedure();
		procedure.start();
	}
	
	public int getId() {
		return id;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	private void openBrowser(String url) {
		if(chrome != null) {
			chrome.close();
		}
		
		chrome = new ChromeDriver(options);
		chrome.get(url);
	}
	
	private void setProcedure() {
		if(procedureName.equalsIgnoreCase("amazon")) {
			procedure = new Amazon(chrome);
		}
		else if(procedureName.equalsIgnoreCase("bestbuy")) {
			procedure = new BestBuy(chrome);
		}
		else {
			procedure = null;
		}
	}
	
	 public static ChromeOptions getChromeOptions() {
    	ChromeOptions options = new ChromeOptions();

//	    	if(System.getenv("CHROMEDRIVER_PATH") != null) {

		options.addArguments("--proxy-server='direct://'", "--proxy-bypass-list=*", "--window-size=1920,1080", "--headless", "--disable-dev-shm-usage", "--disable-gpu");
		LOGGER.info("Heroku Environment detected, adding low resource usage Chrome Options");
//	    	}
		
		return options;
	}
}
