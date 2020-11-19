package org.ventex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ventex.sites.Walmart;

public class App {
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
    public static void main( String[] args ){
    	String basePath = new File("").getAbsolutePath();
    	JSONObject config = parseJSONFile(basePath + "\\configuration.json"); 
    	System.setProperty("webdriver.chrome.driver", basePath + "\\resources\\chromedriver.exe");
    	
        Bot bot1 = new Bot("https://www.walmart.com/ip/PlayStation-5-Console/363472942", config);
        bot1.start();
    }
    
    public static JSONObject parseJSONFile(String filename) {
        String content = "";
        
		try {
			content = new String(Files.readAllBytes(Paths.get(filename)));
		} catch (IOException e) {
			LOGGER.severe("Error reading configuration file");
		}
		
        return new JSONObject(content);
    }
}
