package com.sharpqa.remotewebdriver;


/*
 * This class holds a remotedriver instance and tracks how many times it has been used from the thread pool. 
 * - then work the modules contained within.
 * 
 * @author : Pritesh Gandhi
 * @date   : 02 April 2012
 */


import java.util.concurrent.atomic.AtomicInteger;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PooledRemoteWebDriver {

	
	public static final int MAXTESTSINBROWSERSESSION = 7;
	private RemoteWebDriver remoteWebdriver;
	private AtomicInteger noOfTimesUsed = new AtomicInteger(0);

	public RemoteWebDriver getRemoteWebdriver() {
		return remoteWebdriver;
	}
	public AtomicInteger getNoOfTimesUsed() {
		return noOfTimesUsed;
	}

	public void setNoOfTimesUsed(AtomicInteger noOfTimesUsed) {
		this.noOfTimesUsed = noOfTimesUsed;
	}
	public void setRemoteWebdriver(RemoteWebDriver remoteWebdriver) {
		this.remoteWebdriver = remoteWebdriver;
	}
	
}
