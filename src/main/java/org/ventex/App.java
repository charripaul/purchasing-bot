package org.ventex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONObject;

public class App {
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
    public static void main( String[] args ){
    	String basePath = new File("").getAbsolutePath();
    	JSONObject config = parseJSONFile(basePath + "\\configuration.json"); 
    	System.setProperty("webdriver.chrome.driver", basePath + "\\resources\\chromedriver.exe");
    	
    	int botCount = 1;
    	List<Bot> botList = new ArrayList<>();
    	
    	for(int x=0;x<botCount;x++) {
    		Bot bot = new Bot("https://www.amazon.com/gp/product/B08FC5L3RG/ref=ox_sc_saved_title_4?smid=ATVPDKIKX0DER&psc=1", config);
            bot.run();
            botList.add(bot);
    	}
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
