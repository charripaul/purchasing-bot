package org.ventex;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.ventex.procedures.Amazon;
import org.ventex.procedures.Procedure;

public class Bot implements Runnable{
	private static final Logger LOGGER = Logger.getLogger(Bot.class.getName());
	private int id;
	private String url;
	private WebDriver chrome;
	private Procedure procedure;
	private String procedureName;
	private Map<String, Object> config;
	private Thread thread;
	
	public Bot(int id, String url, String procName, Map<String, Object> config) {
		this.id = id;
		this.url = url;
		this.procedureName = procName;
		this.config = config;
		thread = new Thread(this);
	}
	
	public void start() {
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
		
		chrome = new ChromeDriver();
        chrome.get(url);
	}
	
	private void setProcedure() {
		if(procedureName.equalsIgnoreCase("amazon")) {
			procedure = new Amazon(chrome, (String) config.get("amazonUsername"), (String) config.get("amazonPassword"));
		}
		else {
			procedure = null;
		}
	}
}
