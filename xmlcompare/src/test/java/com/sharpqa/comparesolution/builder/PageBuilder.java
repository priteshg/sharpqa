package com.sharpqa.comparesolution.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

import com.sharpqa.comparesolution.core.ConfigConstants;
import com.sharpqa.comparesolution.domain.*;

/*
 * This Class builds a PageObjectMap  bean which is the top level consisting of Areas>blocks>modules>webElements
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 * 
 */

public class PageBuilder {

	private Page page;

	public PageBuilder(WebDriver driver) throws Exception {

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		List<Section> pageSections = new ArrayList<Section>();

		List<Area> pageAreasOusideScetion = Area.getAreaObjectList(driver, null,ConfigConstants.AREASOUTSIDESECTION);
		
		//Get all sections ...
		for (String configSection : ConfigConstants.SECTIONS) {
			Section section = new SectionBuilder(driver, configSection).getPageSectionBean();
			if (!(section == null)) {
				pageSections.add(section);
			}
		}

		page = new Page(pageSections, pageAreasOusideScetion, driver.getCurrentUrl());
	}

	public Page getPage() {
		return page;
	}

	

}
