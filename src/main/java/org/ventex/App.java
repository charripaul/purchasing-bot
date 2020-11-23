package org.ventex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	public static final ChromeOptions browserOptions = new ChromeOptions();
	
    public static void main( String[] args ){
    	setBrowserOptions();
    	extractDriver("/chromedriver.exe");
    	
    	List<String> links = new ArrayList<>();
    	links.add("https://www.amazon.com/gp/product/B08FC5L3RG/ref=ox_sc_saved_title_4?smid=ATVPDKIKX0DER&psc=1");
    	links.add("https://www.amazon.com/dp/B08FC6MR62/?coliid=INMNG23U2KWCV&colid=13HJVDSXIWZQ&psc=0&ref_=lv_ov_lig_dp_it");
//    	links.add("https://www.bestbuy.com/site/sony-playstation-5-console/6426149.p?skuId=6426149");
//    	links.add("https://www.bestbuy.com/site/marvels-spider-man-miles-morales-standard-launch-edition-playstation-5/6430146.p?skuId=6430146");
    	
    	//1 bootpool for every link, instance count determines number of bots in botpool
    	int instanceCount = 1;
    	List<BotPool> botPoolList = new ArrayList<>();

    	for(int x=0;x<links.size();x++) {
    		BotPool botPool = new BotPool(x, links.get(x), instanceCount);
    		botPool.startAll();
            botPoolList.add(botPool);
    	}
    }
    
    private static void extractDriver(String path) {
        InputStream in = App.class.getResourceAsStream(path);
        File f = new File("Driver");
        
        if (!f.exists()) {
            f.mkdirs();
        }
        
        File driver = new File("Driver" + File.separator + path.substring(1));
        if (!driver.exists()) {
            try {
            	driver.createNewFile();
				FileUtils.copyToFile(in, driver);
			} catch (IOException e) {
				LOGGER.severe("Error copying driver");
			}
        }
        
        if(System.getenv("CHROMEDRIVER_PATH") == null) {
        	System.setProperty("webdriver.chrome.driver", driver.getAbsolutePath());
        }
        else {
        	System.setProperty("webdriver.chrome.driver", System.getenv("CHROMEDRIVER_PATH"));
        }
        
        LOGGER.info("Driver location set");
    }
    
    public static void setBrowserOptions() {
    	if(System.getenv("CHROMEDRIVER_PATH") != null) {
    		LOGGER.info("Heroku Environment detected, adding low resource usage Browser Options");
    		browserOptions.setBinary(System.getenv("GOOGLE_CHROME_BIN"));
    		browserOptions.addArguments("--headless");
    		browserOptions.addArguments("--proxy-server=*");
    		browserOptions.addArguments("--proxy-bypass-list=*");
    		browserOptions.addArguments( "--disable-dev-shm-usage");
    		browserOptions.addArguments("--disable-gpu");
    		browserOptions.addArguments("--no-proxy-server");
    		browserOptions.addArguments("--no-sandbox");
    	}
	}
}
