package com.sharpqa.comparesolution.test;


import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.sharpqa.comparesolution.core.ComparisonConstants;
import com.sharpqa.comparesolution.core.FrameworkDriver;
import com.sharpqa.comparesolution.domain.Section;
import com.sharpqa.helper.GeneralHelper;


public class ComparisonTestSetBaseLine {
	private String id;
	private String url;
	private String browser;
	
	public ComparisonTestSetBaseLine(String browser, String url, String id) {
		this.browser = browser;
		this.url = url;
		this.id = id;
	}

	@Test(description = "Obtain Page Object Map")
	public void createXMLPageObjectMap() throws Exception {		
		
		String tempDirectory = System.getProperty("java.io.tmpdir");	
		String pathOfExpectedXML  = tempDirectory + "\\"+ ComparisonConstants.ROOT_FOLDER +"\\"+ GeneralHelper.getEnvironmentFromURL(url) +"\\" + id + ComparisonConstants.EXPECTED_PATH;
		FrameworkDriver frameworkDriver = new FrameworkDriver(url, pathOfExpectedXML, browser);
		
		
		System.out.println("Formed XML Map of [" + url + "] in [" + pathOfExpectedXML + "]");
		Reporter.log("Formed XML Map of [" + url + "] in [" + pathOfExpectedXML + "]");
		
		
		
		//Fail those that have no modules at all
		List<Section> sections = frameworkDriver.getPage().getSections();
		
		Boolean pass = false;
		for(Section section: sections){
			if(!section.getSectionAreaBeans().isEmpty()) {
				pass = true;
			}
		}
		if(!pass){
			Assert.fail("No Modules found for [" + url + "] ");
		}
		//---------------------------------------------------------
	}
	
	
	
	
	
	
	
	
	

}