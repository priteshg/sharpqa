package com.sharpqa.comparesolution.core;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

import com.sharpqa.comparesolution.builder.PageBuilder;
import com.sharpqa.comparesolution.domain.Page;
import com.sharpqa.comparesolution.xstream.XStreamConfiguration;
import com.sharpqa.remotewebdriver.PooledRemoteWebDriver;
import com.sharpqa.remotewebdriver.RemoteDriverBuilder;
import com.thoughtworks.xstream.XStream;

/*
 * This Class is used to obtain an XML Representation of a GTI Web site Page using the bean builders
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 * 
 */

public class FrameworkDriver {
	private static int COMPLETEDNUMBER = 0;
	private PooledRemoteWebDriver pooledRemotedriver;
	private String URL;
	private String pathOfXML;
	private String browser;
	private Page page;

	public FrameworkDriver(String URL, String pathOfXML, String browser) throws Exception {
		this.URL = URL;
		this.pathOfXML = pathOfXML;
		this.browser = browser;
		run();
	}

	public void run() throws InterruptedException {
		setPage();
		if (page != null) {
			writeXMLFile(pathOfXML, page);
			System.err.print("NUMBER OF COMPLETED XML CONVERSIONS:=" + COMPLETEDNUMBER++);
		} else {
			Assert.fail("FAILED TO GET URL[" + URL + "]");
			System.err.println("FAILED TO GET URL[" + URL + "] ");
		}
	}

	private void setPage() throws InterruptedException {
		int numberOfTries = 0;
		page = null;

		while (numberOfTries < 5) { // Try this 20 times!
			try {
				getRemoteDriverInstance();
				//RemoteWebDriver driver = pooledRemotedriver.getRemoteWebdriver();
				page = new PageBuilder(pooledRemotedriver.getRemoteWebdriver()).getPage();
				break;
			} catch (Exception e) {
				numberOfTries++;
				Thread.sleep(numberOfTries * 1000); // increase the wait based
													// // on number of attempts
			} finally {
				try {
					releaseRemoteDriverInstance();
				} catch (Exception e) {
					System.out.println("Expection" + e);
				}
			}

		}
	}

	private void getRemoteDriverInstance() throws Exception {
		System.out.println("ATTEMPTING:[" + URL + "]");
		pooledRemotedriver = RemoteDriverBuilder.getInstance().getRemoteDriver();
		pooledRemotedriver.getRemoteWebdriver().get(URL);
		System.out.println("STARTED:[" + URL + "]");
	}

	private void releaseRemoteDriverInstance() {
		if (pooledRemotedriver != null) {
			RemoteDriverBuilder.getInstance().releaseRemoteDriver(pooledRemotedriver);
		}
	}

	private static void writeXMLFile(String path, Page page) {
		XStream xstream = new XStreamConfiguration().getXStream();

		// Write to a file in the file system
		try {
			File file = new File(path);
			file.delete();
			FileUtils.writeStringToFile(file, xstream.toXML(page), "UTF-8");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Page getPage() {
		return page;
	}

}
