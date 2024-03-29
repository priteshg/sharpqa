package com.sharpqa.comparesolution.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.*;

import com.sharpqa.comparesolution.domain.*;
import com.sharpqa.helper.WebPageHelpers;

/*
 * This class builds a list of module Beans from a given block element ( think of it as, Page contains Areas>block>Module>WebElements)
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 * 
 */

public class ModuleBuilder {
	WebDriver driver;

	private WebElement sectionAreaElement;
	List<Module> modules = new ArrayList<Module>();

	private String moduleCssLocator = ".module";
	private String componentHeaderCssLocator = ".rowHead>*";
	public static final String[] EXCEPTION_MODULES  = {"SN03"};
	
	public ModuleBuilder(WebDriver driver, WebElement sectionAreaElement) throws InterruptedException {
		this.driver = driver;
		this.sectionAreaElement = sectionAreaElement;
		setModuleBeans();
	}

	private void setModuleBeans() throws InterruptedException {
		//Get webelements of webElementModules 
		List<WebElement> webElementModules = WebPageHelpers.createWebElementsList(driver, sectionAreaElement, By.cssSelector(moduleCssLocator));
		
		
		if (WebPageHelpers.testWebElementListNotEmpty(webElementModules)) {
			for (WebElement webElementModule : webElementModules) {
				String moduleType = getElementModuleNameFromClass(webElementModule);
				
				//Skip Exception modules such as the Twitter module 
				if (!Arrays.asList(EXCEPTION_MODULES).contains(moduleType)) {
					Module module = new Module(moduleType);
					setModuleHeaderFields(webElementModule, module);
					
					System.out.println();
					System.out.println("[" + moduleType + "]");
					ModuleElementGroupBuilder moduleElementGroupBuilder = new ModuleElementGroupBuilder(driver, webElementModule, moduleType);

					// Get the WebElements outside any components
					module.setWebElementSetBeans(moduleElementGroupBuilder.getNonComponentElementGroups());

					// Get the WebElements inside any components
					module.setWebElementSetComponentBeans(moduleElementGroupBuilder.getComponentElementGroups());

					modules.add(module); // Populate Module beans
				}
			}
		}

	}

	public List<Module> getModules() {
		return modules;
	}

	private String getElementModuleNameFromClass(WebElement webElement) {
		String searchFor = "moduleID-";
		String className = webElement.getAttribute("class");
		className = className.substring(className.indexOf(searchFor) + searchFor.length());
		className = className.split(" ")[0];
		webElement.getAttribute("class");
		return className;
	}

	private void setModuleHeaderFields(WebElement moduleWebElemnt, Module module) {
		List<WebElement> moduleHeaderWebElemnts = WebPageHelpers.createWebElementsList(driver, moduleWebElemnt, By.cssSelector(componentHeaderCssLocator));

		String moduleHeaderWebElemnt = "";
		if (WebPageHelpers.testWebElementListNotEmpty(moduleHeaderWebElemnts)) {
			moduleHeaderWebElemnt = moduleHeaderWebElemnts.get(0).getText();
			List<WebElement> moduleHeaderLinks = WebPageHelpers.createWebElementsList(driver, moduleHeaderWebElemnts.get(0), By.tagName("a"));
			if (WebPageHelpers.testWebElementListNotEmpty(moduleHeaderLinks)) {
				module.setHeaderLink(new Link(moduleHeaderLinks.get(0).getText(), moduleHeaderLinks.get(0).getAttribute("href"), moduleHeaderLinks.get(0).getAttribute("color")));
				moduleHeaderWebElemnt = moduleHeaderWebElemnt.replace(moduleHeaderLinks.get(0).getText(), "");
			}
			module.setHeader(moduleHeaderWebElemnt);
		}

	}

}
