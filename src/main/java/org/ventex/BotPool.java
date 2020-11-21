package org.ventex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BotPool {
	private static final Logger LOGGER = Logger.getLogger(BotPool.class.getName());
	private int id;
	private String url;
	private String procedureName;
	private boolean isValid;
	private Map<String, Object> config;
	private List<Bot> bots;
	private Thread controlThread;
	private int instanceCount;
	
	public BotPool(int id, String url, int instanceCount, Map<String, Object> config) {
		this.id = id;
		this.url = url;
		this.instanceCount = instanceCount;
		this.config = config;
		bots = new ArrayList<>();
		procedureName = determineSite(url);
		
		if(procedureName != null) {
			this.isValid = true;
			setupBots();
		}
		else {
			this.isValid = false;
		}
	}
	
	public void startAll() {
		if(isValid) {
			controlThread.start();
		}
		else {
			LOGGER.severe("Website not recognized or not supported.\nURL: " + url);
			LOGGER.severe("Terminating Bot Pool");
		}
	}
	
	private void setupBots() {
		for(int x=0;x<instanceCount;x++) {
			Bot bot = new Bot(x, url, procedureName, config);
			bots.add(bot);
		}
		
		setupControlThread();
	}
	
	private void setupControlThread() {
		controlThread = new Thread(() -> {
			
			while(true) {
				for(int x=0;x<bots.size();x++) {
					Bot bot = bots.get(x);
					if(bot.getThread() == null || bot.getThread().isAlive() == false) {
						LOGGER.info("Dead Bot found.\nRestarting " + procedureName + "-" + id + " Bot-" + bot.getId());
						bot.start();
					}
				}
				
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					LOGGER.severe("Error sleeping in control thread");
				}
			}
			
		});
	}
	
	private static String determineSite(String url) {
		if(url.toLowerCase().contains("amazon")) {
			return "amazon";
		}
		
		return null;
	}
}
