package com.sharpqa.comparesolution.builder;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;

import com.sharpqa.comparesolution.core.ConfigConstants;
import com.sharpqa.comparesolution.domain.*;
import com.sharpqa.helper.WebPageHelpers;

/*
 * This Class builds a Module, which consists of a number of webelements in either a component or outside a component
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 * 
 */

public class ModuleElementGroupBuilder {
	private WebDriver driver;
	private static final String[] componentCssLocators = ConfigConstants.COMPONENTLOCATORS;
	private WebElement moduleElement;
	private String moduleType;
	
	private List<WebElement> moduleComponents = new ArrayList<WebElement>();
	private List<ElementGroup> componentElementGroups = new ArrayList<ElementGroup>();
	private List<ElementGroup> nonComponentElementGroups = new ArrayList<ElementGroup>();

	public ModuleElementGroupBuilder(WebDriver driver, WebElement moduleElement, String moduleType) throws InterruptedException {
		this.driver = driver;
		this.moduleElement = moduleElement;
		this.moduleType = moduleType;

		setModuleComponents();
		setNonComponentElementGroups();
		setComponentElementGroups();
	}

	
	private void setModuleComponents() {
		for (String componentLocator : componentCssLocators) {
			moduleComponents = WebPageHelpers.createWebElementsList(driver, moduleElement, By.cssSelector(componentLocator));
			if (WebPageHelpers.testWebElementListNotEmpty(moduleComponents)) {
				break;
			}
		}
	}

	private void setNonComponentElementGroups() throws InterruptedException {
		List<WebElement> tagsOusideComponents = new ArrayList<WebElement>();

		// get all the tags outside of the
		tagsOusideComponents = WebPageHelpers.createWebElementsList(driver, moduleElement, By.cssSelector(".moduleID-" + moduleType + ">:not(.componentRow)"));

		for (WebElement tagOusideComponent : tagsOusideComponents) {
			LinkBuilder linkTypeElements = new LinkBuilder(driver, tagOusideComponent, 0, moduleType);
			ElementGroup elementGroup = new ElementGroup();
			elementGroup.setPictureLinks(linkTypeElements.getImageLinks());
			elementGroup.setLinks(linkTypeElements.getLinks());
			elementGroup.setTextBLocks(TextBlockBuilder.getTextElements(driver, tagOusideComponent));
			elementGroup.setPictures(ImageBuilder.getPictureElements(driver, tagOusideComponent, 0));

			if (elementGroup.isValid()) {
				nonComponentElementGroups.add(elementGroup);
			}
		}
	}

	private void setComponentElementGroups() throws InterruptedException {
		int cntComponents = 0;
		for (WebElement moduleComponent : moduleComponents) {
			ElementGroup elementGroup = new ElementGroup();
			System.out.println("COMPONENT:" + cntComponents);
			setComponentFields(elementGroup, moduleComponent, cntComponents);
			componentElementGroups.add(elementGroup);
			cntComponents++;
		}
	}

	private void setComponentFields(ElementGroup elementGroup, WebElement componentElement, int componentNo) throws InterruptedException {
		LinkBuilder linkTypeElements = new LinkBuilder(driver, componentElement, componentNo, moduleType);
		List<ImageLink> imageLinks = linkTypeElements.getImageLinks();
		List<Link> links = linkTypeElements.getLinks();
		
		List<Image> images = ImageBuilder.getPictureElements(driver, componentElement, componentNo);
		List<TextBlock> textBlocks = TextBlockBuilder.getTextElements(driver, componentElement);
		elementGroup.setPictureLinks(imageLinks);
		elementGroup.setLinks(links);
		elementGroup.setTextBLocks(textBlocks);
		elementGroup.setPictures(images);
	}

	public List<ElementGroup> getComponentElementGroups() {
		if (componentElementGroups.isEmpty()) {
			return null;
		} else {
			return componentElementGroups;
		}
	}

	public List<ElementGroup> getNonComponentElementGroups() {
		if (nonComponentElementGroups.isEmpty()) {
			return null;
		} else {
			return nonComponentElementGroups;
		}
	}

}
