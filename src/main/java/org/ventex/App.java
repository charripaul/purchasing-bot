package org.ventex;

import java.io.File;

public class App {
    public static void main( String[] args ){
    	String basePath = new File("").getAbsolutePath();
    	System.setProperty("webdriver.chrome.driver", basePath + "\\resources\\chromedriver.exe");
    	
        Bot bot1 = new Bot();
        bot1.setup("https://www.walmart.com/ip/PlayStation-5-Console/363472942");
    }
}
