package com.sharpqa.comparesolution.builder;

import java.util.List;

import org.openqa.selenium.*;

import com.sharpqa.comparesolution.core.ConfigConstants;
import com.sharpqa.comparesolution.domain.*;
import com.sharpqa.helper.WebPageHelpers;

/*
 * This class builds a Page Area Bean which is dependent on the "Area Name" that is given during its conception. From this it can retrieve all blocks 
 * - then work the modules contained within.
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 */

public class SectionBuilder {

	private WebDriver driver;
	private List<WebElement> sectionWebElements;
	private String pageSectionName;
	private Section section;

	public SectionBuilder(WebDriver driver, String sectionName) throws InterruptedException {
		this.pageSectionName = sectionName;

		this.driver = driver;
		String cssselector = "." + sectionName;
		
		sectionWebElements = WebPageHelpers.createWebElementsList(driver, By.cssSelector(cssselector));	
		if(WebPageHelpers.testWebElementListNotEmpty(sectionWebElements)){
		setPageSectionBean(sectionWebElements.get(0));
		}
	}

	private void setPageSectionBean(WebElement sectionElement) throws InterruptedException {		
		List<Area> areas = Area.getAreaObjectList(driver,sectionElement, ConfigConstants.SECTIONAREAS);		
		section = new Section(pageSectionName, areas);
	}

	public Section getPageSectionBean() {
		return section;
	}

}