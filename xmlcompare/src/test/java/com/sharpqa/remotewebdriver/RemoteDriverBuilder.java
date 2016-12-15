package com.sharpqa.remotewebdriver;

/*
 * This is a SINGLETON class which handles the pool of  remote driver threads 
 *
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 */

import java.net.MalformedURLException;

import com.sharpqa.comparesolution.core.ComparisonConstants;
import com.sharpqa.comparesolution.core.ConfigConstants;

public class RemoteDriverBuilder {
	private static final String HUB_URL = ConfigConstants.HUB_URL;
	private static RemoteDriverBuilder builder;

	private RemoteWebDriverObjectPool remoteWebDriverObjectPool;

	public static synchronized RemoteDriverBuilder getInstance() {
		if (builder == null) {
			try {
				builder = new RemoteDriverBuilder();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		return builder;
	}

	private RemoteDriverBuilder() throws MalformedURLException {
		try {
			System.out.println("Instance [" + builder + "] starting [" + ComparisonConstants.BROWSER + "] Remote driver ");
			remoteWebDriverObjectPool = new RemoteWebDriverObjectPool(HUB_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PooledRemoteWebDriver getRemoteDriver() {
		return remoteWebDriverObjectPool.borrowObject();
	}

	public void releaseRemoteDriver(PooledRemoteWebDriver driver) {
		remoteWebDriverObjectPool.returnObject(driver);
	}

	public void closeAll() {
		while (remoteWebDriverObjectPool.getIdleCount() > 0) {
			try {
				getRemoteDriver().getRemoteWebdriver().quit();
			} catch (Exception e) {
				break;
			}
		}
	}
}