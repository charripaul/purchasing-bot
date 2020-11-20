package org.ventex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
    public static void main( String[] args ){
    	System.out.println("Testing stdout logger");
    	setBinaryLocation();
    	Map<String, Object> config = getConfigData();
    	extractAndRunDriver("/chromedriver.exe");
    	
    	int botCount = 1;
    	List<Bot> botList = new ArrayList<>();

    	for(int x=0;x<botCount;x++) {
    		Bot bot = new Bot("https://www.amazon.com/gp/product/B08FC5L3RG/ref=ox_sc_saved_title_4?smid=ATVPDKIKX0DER&psc=1", config);
    		bot.run();
            botList.add(bot);
    	}
    }
    
    public static Map<String, Object> getConfigData() {
    	Map<String, Object> map = new HashMap<>();
        map.put("amazonUsername", System.getenv("amazonUsername"));
        map.put("amazonPassword", System.getenv("amazonPassword"));
        
        return map;
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
        LOGGER.info("Chrome driver location set");
    }
    
    private static void setBinaryLocation() {
    	ChromeOptions options = new ChromeOptions();
    	options.setBinary("GOOGLE_CHROME_BIN");
    	options.addArguments("--headless", "--disable-dev-shm-usage", "--no-sandbox");
    	LOGGER.info("Chrome binary location set");
    }
}
