package com.sharpqa.comparesolution.test;

import java.util.List;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;

import com.sharpqa.comparesolution.reader.*;
import com.sharpqa.remotewebdriver.RemoteDriverBuilder;

public class ComparisonTestFactory {
	private String browser = "firefox";
	private String testType;

	@Factory
	@Parameters({ "testType" })
	public Object[] createInstances(String testType) {
		String url;
		String path;
		Object[] result = null;
		this.testType = testType;
		try {
			List<String> dataSet = new ReadInputFile().getContents();
			int n = 0;
			result = new Object[dataSet.size()];
			for (String data : dataSet) {
				url = data.split(",")[0].trim();
				path = data.split(",")[1].trim();
				System.out.println("url = [" + url + "] path = [" + path + "]");
				if (testType.equals("baseline")) {
					result[n] = new ComparisonTestSetBaseLine(browser, url, path);
				} else if (testType.equals("compareTest")) {
					result[n] = new ComparisonTest(browser, url, path);
				} else if (testType.equals("compareOnlyTest")) {
					result[n] = new ComparisonCompareOnlyTest(browser, url, path);
				}
				n++;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}
	
	@AfterSuite(alwaysRun = true)
	public void quitAllbrowsers() {
		if(!(testType.equals("compareOnlyTest"))){
		System.out.println("Closing all browsers");
		RemoteDriverBuilder.getInstance().closeAll();
		}
	}
}