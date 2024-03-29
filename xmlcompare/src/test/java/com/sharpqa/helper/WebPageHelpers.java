package com.sharpqa.helper;

import org.openqa.selenium.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class WebPageHelpers {

	
	//----------------------These functions require a driver
	public static List<WebElement> createWebElementsList(WebDriver driver, WebElement component, By by) {
		List<WebElement> webElements;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS); // Quickly
		webElements = component.findElements(by);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return webElements;
	}

	public static List<WebElement> createWebElementsList(WebDriver driver, By by) {
		List<WebElement> webElements;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS); // Quickly
		webElements = driver.findElements(by);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return webElements;
	}
	
 
	public static boolean testWebElementListNotEmpty(List<WebElement> webElements) {
		if (webElements == null || webElements.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}
	
}
