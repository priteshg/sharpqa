package com.sharpqa.comparesolution.domain;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sharpqa.comparesolution.builder.ModuleBuilder;
import com.sharpqa.comparesolution.builder.NonModuleElementGroupBuilder;
import com.sharpqa.helper.WebPageHelpers;

public class Area {
	private String name;
	private List<ElementGroup> elementGroups = new ArrayList<ElementGroup>();
	private List<Module> modules;

	public Area(String name, List<Module> modules, List<ElementGroup> elementGroups) {
		this.name = name;
		this.elementGroups = elementGroups;
		this.modules = modules;
	}

	public List<ElementGroup> getWebElementSetBeans() {
		return elementGroups;
	}

	public void setWebElementSetBeans(List<ElementGroup> elementGroups) {
		this.elementGroups = elementGroups;
	}

	public String getAreaName() {
		return name;
	}

	public void setAreaName(String areaName) {
		this.name = areaName;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public List<Module> getModules() {
		return modules;
	}

	public static List<Area> getAreaObjectList(WebDriver driver, WebElement sectionElement, String[] areas) throws InterruptedException {
		List<Area> areaObjects = new ArrayList<Area>();
		
		
		
		for (String area : areas) {

			String cssselector = "." + area;
			List<WebElement> areaElements;
			
			if (sectionElement == null) {
				areaElements = WebPageHelpers.createWebElementsList(driver, By.cssSelector(cssselector));
			} else {
				areaElements = WebPageHelpers.createWebElementsList(driver, sectionElement, By.cssSelector(cssselector));
			}

			
			if (WebPageHelpers.testWebElementListNotEmpty(areaElements))

			{
				System.out.println("Area FOUND :=[" + area + "]");
				WebElement areaElement = areaElements.get(0);
				// ------------------------------------------------
				NonModuleElementGroupBuilder webElementSetOutsideBeanBuilder = new NonModuleElementGroupBuilder(driver, area, areaElement);
				// Get the modules of that Area ---------------------------
				List<Module> modules = new ArrayList<Module>();
				modules.addAll(new ModuleBuilder(driver, areaElement).getModules());
				areaObjects.add(new Area(area, modules, webElementSetOutsideBeanBuilder.getWebElementSetBeans()));
				// --------------------------------------------------------
			} else {
				System.out.println("Section Area NOT-FOUND :=[" + area + "]");
			}
		}

		return areaObjects;
	}

}
