package org.ventex;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Bot {
	public void setup(String url) {
		openBrowser(url);
		startDriver();
	}
	
	public void openBrowser(String url) {
		WebDriver chrome = new ChromeDriver();
        chrome.get(url);
	}
	
	public void startDriver() {
		String basePath = new File("").getAbsolutePath();
	    System.out.println(basePath);
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(basePath + "\\resources\\chromedriver.exe", null, new File(basePath + "\\resources"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
