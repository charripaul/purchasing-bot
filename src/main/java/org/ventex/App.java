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
    	Map<String, Object> config = getConfigData();
    	extractDriver("/chromedriver.exe");
    	
    	List<String> links = new ArrayList<>();
//    	links.add("https://www.amazon.com/gp/product/B08FC5L3RG/ref=ox_sc_saved_title_4?smid=ATVPDKIKX0DER&psc=1");
//    	links.add("https://www.amazon.com/dp/B08FC6MR62/?coliid=IH90F1G2RL9K5&colid=6DFCB5FA7VGW&psc=0&ref_=lv_ov_lig_dp_it_im");
    	links.add("https://www.amazon.com/Marvels-Avengers-PlayStation-4/dp/B07SFZX5CH/?_encoding=UTF8&pd_rd_w=9NhM0&pf_rd_p=42e00508-3a02-4e82-9e70-6ec0c1941802&pf_rd_r=SKF8F41F7YXABFKD6XX0&pd_rd_r=8c67b2ff-b443-4eb0-95a2-6d6bb63c92ad&pd_rd_wg=NyRLA&ref_=pd_gw_trq_rep_sims_gw");
    	
    	//1 bootpool for every link, instance count determines number of bots in botpool
    	int instanceCount = 2;
    	List<BotPool> botPoolList = new ArrayList<>();

    	for(int x=0;x<links.size();x++) {
    		BotPool botPool = new BotPool(x, links.get(x), instanceCount, config);
    		botPool.startAll();
            botPoolList.add(botPool);
    	}
    }
    
    public static Map<String, Object> getConfigData() {
    	Map<String, Object> map = new HashMap<>();
        map.put("amazonUsername", System.getenv("amazonUsername"));
        map.put("amazonPassword", System.getenv("amazonPassword"));
        
        return map;
    }
    
    private static void extractDriver(String path) {
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
        
        if(System.getenv("CHROMEDRIVER_PATH") == null) {
        	System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
        }
        else {
        	System.setProperty("webdriver.chrome.driver", System.getenv("CHROMEDRIVER_PATH"));
        }
        
        LOGGER.info("Chrome driver location set");
    }
    
    public static ChromeOptions getChromeOptions() {
    	ChromeOptions options = new ChromeOptions();
//    	options.setBinary("GOOGLE_CHROME_BIN");
    	if(System.getenv("CHROMEDRIVER_PATH") != null) {
    		options.addArguments("--headless", "--disable-dev-shm-usage", "--no-sandbox");
    		LOGGER.info("Heroku Environment detected, adding low resource usage Chrome Options");
    	}
    	
    	return options;
    }
}
