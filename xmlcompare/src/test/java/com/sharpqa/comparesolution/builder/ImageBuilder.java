package com.sharpqa.comparesolution.builder;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;

import com.sharpqa.comparesolution.domain.*;
import com.sharpqa.helper.WebPageHelpers;

/*
 * This class builds a Image Bean, the images contained within a WebElement Set belonging to a Module
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 */

public class ImageBuilder {
	//This is the set of locators used to identify an image. Note we don't use img as this will pick up image links as well.
	private static final String[] imageCssLocators = { ":not(a)>div>img", ":not(a)>li>img" };
	
	
	public static List<Image> getPictureElements(WebDriver driver, WebElement webElementSet, int componentNo) {
		List<WebElement> elements = new ArrayList<WebElement>();

		for (String selector : imageCssLocators) {
			elements.addAll(WebPageHelpers.createWebElementsList(driver, webElementSet, By.cssSelector(selector)));
		}
		
		//Add to the list if the webElementSet is an image it's self
		if (webElementSet.getTagName().equals("img")) {
			elements.add(0, webElementSet);
		}
		
		
		List<Image> Images = new ArrayList<Image>();
		//Iterate elements list to populate imageBeans list ImageBeans
		
		if (WebPageHelpers.testWebElementListNotEmpty(elements)) {
			for (WebElement element : elements) {
				Image image = new Image(element.getAttribute("src"), element.getAttribute("width"), element.getAttribute("height"));
				Images.add(image);
			}

		}
		
		if (Images.isEmpty()) {
			return null;
		} else {
			return Images;
		}
	}

}
