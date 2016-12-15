package com.sharpqa.comparesolution.test;

import java.io.FileReader;

import org.custommonkey.xmlunit.XMLTestCase;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.sharpqa.comparesolution.core.ComparisonConstants;
import com.sharpqa.comparesolution.core.FrameworkDriver;
import com.sharpqa.comparesolution.xmlunit.XMLComparison;
import com.sharpqa.helper.GeneralHelper;

public class ComparisonTest extends XMLTestCase {

	private String URL;
	private String pathOfActualXML;
	private String pathOfExpectedXML;
	private String browser;
	private String id;
	
	FileReader xmlActual = null;
	FileReader xmlExpected = null;

	
	
	public ComparisonTest(String browser, String pageURL, String id){
		this.browser = browser;
		this.URL = pageURL;
		this.id = id;
	}
		
	@Test(description = "Obtain Page Object Map")
	public void createXMLPageObjectMap() throws Exception {
		Reporter.log("createXMLPageObjectMap - url = [" + URL + "] id = [" + id + "]");
		System.out.println("url = [" + URL + "] id = [" + id + "]");

		String tempDirectory = System.getProperty("java.io.tmpdir");
		this.pathOfExpectedXML  = tempDirectory + "\\"+ ComparisonConstants.ROOT_FOLDER +"\\"+ GeneralHelper.getEnvironmentFromURL(URL) +"\\" + id + ComparisonConstants.EXPECTED_PATH;
		this.pathOfActualXML = tempDirectory + "\\"+ ComparisonConstants.ROOT_FOLDER +"\\"+ GeneralHelper.getEnvironmentFromURL(URL) +"\\" + id + ComparisonConstants.ACTUAL_PATH;	
		
		new FrameworkDriver(URL, pathOfActualXML, browser);
		Reporter.log("Formed XML in [" + pathOfActualXML + "]");
	}

	@Test(dependsOnMethods = { "createXMLPageObjectMap" }, description = "compare XMLs with Expected")
	public void testXMLPageObjectMapAgainstExpeted() throws Exception {
		Reporter.log("createXMLPageObjectMap Page object Map of - url = [" + URL + "] id = [" + id + "]");
		logReportDescription();
		XMLComparison xmlComparison = new XMLComparison(new FileReader(pathOfExpectedXML), new FileReader(pathOfActualXML));
		if (xmlComparison.isTestFailed()) {
			Assert.fail(xmlComparison.getOutputMessage());
		}
	}

	private void logReportDescription() throws Exception {
		Reporter.log("BROWSER: " + browser + " comparing ACTUAL xml [" + pathOfActualXML + "]\n <a href='file:///" + pathOfActualXML + "' target='_blank'>ACTUAL</a> against EXPECTED xml [" + pathOfExpectedXML
				+ "]\n<a href='file:///" + pathOfExpectedXML + "' target='_blank'>EXPECTED</a>");
		System.out.println("BROWSER: " + browser + " comparing ACTUAL xml [" + pathOfActualXML + "]\n against EXPECTED xml [" + pathOfExpectedXML + "]");

	}
}
