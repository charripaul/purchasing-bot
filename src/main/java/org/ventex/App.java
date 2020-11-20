package org.ventex;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class App {
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
    public static void main( String[] args ){
    	JSONObject config = parseJSONFile("/configuration.json");
    	extractAndRunDriver("/chromedriver.exe");
    	
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
    	
    	try(InputStream in = App.class.getResourceAsStream(filename)){
    		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    		for (String line; (line = reader.readLine()) != null; content += line);
    	} catch (IOException e1) {
			LOGGER.severe("Error parsing configuration file");
		}
		
        return new JSONObject(content);
    }
    
    private static void extractAndRunDriver(String path) {
        InputStream in = App.class.getResourceAsStream(path);
        File f = new File("Driver");
        
        if (!f.exists()) {
            f.mkdirs();
        }
        
        File chromeDriver = new File("Driver" + File.separator + "chromedriver.exe");
        if (!chromeDriver.exists()) {
            try {
            	chromeDriver.createNewFile();
				FileUtils.copyToFile(in, chromeDriver);
			} catch (IOException e) {
				LOGGER.severe("Error copying driver");
			}
        }
        
        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
    }
}
