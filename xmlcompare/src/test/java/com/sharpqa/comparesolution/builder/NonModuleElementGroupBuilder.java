package com.sharpqa.comparesolution.builder;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;

import com.sharpqa.comparesolution.domain.*;
import com.sharpqa.helper.WebPageHelpers;

/*
 * This Class builds a WebElementSetBean, which consists of a number of webelements in outside a module
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 * 
 */

public class NonModuleElementGroupBuilder {
	private WebDriver driver;
	private String areaName;
	private List<ElementGroup> elementGroups = new ArrayList<ElementGroup>();
	private WebElement sectionAreaElement;

	public NonModuleElementGroupBuilder(WebDriver driver, String areaName, WebElement sectionAreaElement) throws InterruptedException {
		this.driver = driver;
		this.sectionAreaElement = sectionAreaElement;
		this.areaName = areaName;
		setWebElementSetsOusideComponent();
		

	}

	private void setWebElementSetsOusideComponent() throws InterruptedException {
		List<WebElement> tagsOusideModule = new ArrayList<WebElement>();

		// get all the tags outside of the
		tagsOusideModule = WebPageHelpers.createWebElementsList(driver, sectionAreaElement, By.cssSelector("."+areaName+" div[class*='article-']>:not(.module)"));

		
		
		for (WebElement tagOusideModule : tagsOusideModule) {
			LinkBuilder linkTypeElements = new LinkBuilder(driver, tagOusideModule, 0, "");
			ElementGroup elementGroup = new ElementGroup();
			elementGroup.setPictureLinks(linkTypeElements.getImageLinks());
			elementGroup.setLinks(linkTypeElements.getLinks());
			elementGroup.setTextBLocks(TextBlockBuilder.getTextElements(driver, tagOusideModule));
			elementGroup.setPictures(ImageBuilder.getPictureElements(driver, tagOusideModule, 0));

			if (elementGroup.isValid()) {
				elementGroups.add(elementGroup);
			}
		}
	}

	public List<ElementGroup> getWebElementSetBeans() {
		if (elementGroups.isEmpty()) {
			return null;
		} else {
			return elementGroups;
		}
	}

}
