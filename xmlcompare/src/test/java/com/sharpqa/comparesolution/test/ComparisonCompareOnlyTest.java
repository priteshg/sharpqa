package com.sharpqa.comparesolution.test;

import java.io.FileReader;

import org.custommonkey.xmlunit.XMLTestCase;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.sharpqa.comparesolution.core.ComparisonConstants;
import com.sharpqa.comparesolution.xmlunit.XMLComparison;
import com.sharpqa.helper.GeneralHelper;

public class ComparisonCompareOnlyTest extends XMLTestCase {

	private String URL;
	private String pathOfActualXML;
	private String pathOfExpectedXML;
	private String browser;
	private String id;

	FileReader xmlActual = null;
	FileReader xmlExpected = null;

	public ComparisonCompareOnlyTest(String browser, String pageURL, String id) {
		this.browser = browser;
		this.URL = pageURL;
		this.id = id;
		String tempDirectory = System.getProperty("java.io.tmpdir");
		this.pathOfExpectedXML  = tempDirectory + "\\"+ ComparisonConstants.ROOT_FOLDER +"\\"+ GeneralHelper.getEnvironmentFromURL(URL) +"\\" + id + ComparisonConstants.EXPECTED_PATH;
		this.pathOfActualXML = tempDirectory + "\\"+ ComparisonConstants.ROOT_FOLDER +"\\"+ GeneralHelper.getEnvironmentFromURL(URL) +"\\" + id + ComparisonConstants.ACTUAL_PATH;	
	}

	
	@Test
	public void testXMLPageObjectMapAgainstExpeted() throws Exception {
		Reporter.log("createXMLPageObjectMap Page object Map of - url = [" + URL + "] id = [" + id + "]");
		logReportDescription();
		XMLComparison xmlComparison = new XMLComparison(new FileReader(pathOfExpectedXML), new FileReader(pathOfActualXML));
		if (xmlComparison.isTestFailed()) {
			Assert.fail(xmlComparison.getOutputMessage());
		}
	}

	private void logReportDescription() throws Exception {
		Reporter.log("BROWSER: " + browser + " comparing ACTUAL xml [" + pathOfActualXML + "]\n <a href='file:///" + pathOfActualXML + "' target='_blank'>ACTUAL</a> against EXPECTED xml ["
				+ pathOfExpectedXML + "]\n<a href='file:///" + pathOfExpectedXML + "' target='_blank'>EXPECTED</a>");
		System.out.println("BROWSER: " + browser + " comparing ACTUAL xml [" + pathOfActualXML + "]\n against EXPECTED xml [" + pathOfExpectedXML + "]");

	}
}
