package org.ventex;

public class App {
    public static void main( String[] args ){
    	System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
    	
        Bot bot1 = new Bot();
        bot1.setup("https://www.walmart.com/ip/PlayStation-5-Console/363472942");
    }
}
