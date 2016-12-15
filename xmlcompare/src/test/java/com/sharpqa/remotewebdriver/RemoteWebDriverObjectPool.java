package com.sharpqa.remotewebdriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.sharpqa.comparesolution.core.ComparisonConstants;

public class RemoteWebDriverObjectPool implements PoolableObjectFactory {

    private int maxSize = 10;
    private int maxIdle = 10;
    private int minIdle = 2;
    private long maxWait = 10000000000L;
    private long timeBetweenEvictionRunsMillis = -1L;
    private long minEvictableIdleTimeMillis = 1800000L;
    private long whenExhaustedAction = 1;
    private GenericObjectPool internalPool;

    private final String hubUrl;

    public RemoteWebDriverObjectPool(String hubUrl) {
        this.hubUrl = hubUrl;
    }

    public synchronized void init() {
        internalPool = new GenericObjectPool(this);
        internalPool.setMaxActive(getMaxSize());
        internalPool.setMaxIdle(getMaxIdle());
        internalPool.setMinIdle(getMinIdle());
        internalPool.setMaxWait(getMaxWait());
        internalPool.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
        internalPool.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());

        try {
            internalPool.addObject();
        } catch (Exception e) {
            throw new RuntimeException("unable to add rules session to pool", e);
        }
    }

    public synchronized PooledRemoteWebDriver borrowObject() {
        PooledRemoteWebDriver object = null;

        if (internalPool == null) {
            init();
        }
        System.err.println("pre BORROW " + getPoolName() + " from pool:" + "\tactive count is '" + getActiveCount() + "', idle count is '" + getIdleCount());
        try {
            object = (PooledRemoteWebDriver) internalPool.borrowObject();// this
            // line
            // fails
            object.getNoOfTimesUsed().incrementAndGet();
            if (object.getNoOfTimesUsed().get() > PooledRemoteWebDriver.MAXTESTSINBROWSERSESSION) {

                try {
                    object.getRemoteWebdriver().quit();
                } catch (Exception e) {
                    System.out.println("could not quit browser - Browser maybe it closed on its own");
                }
                object.setRemoteWebdriver(makeObject().getRemoteWebdriver());
                object.setNoOfTimesUsed(new AtomicInteger(1));
            }
        } catch (Exception e) {
            throw new RuntimeException("unable to BORROW " + getPoolName() + " from pool", e);
        }

        System.err.println("post BORROW " + getPoolName() + " from pool:" + "\tactive count is '" + getActiveCount() + "', idle count is '" + getIdleCount());

        return object;
    }

    public void returnObject(PooledRemoteWebDriver object) {
        System.err.println("pre RETURN " + getPoolName() + " to pool:" + "\tactive count is '" + getActiveCount() + "', idle count is '" + getIdleCount());

        try {
            internalPool.returnObject(object);
        } catch (Exception e) {
            throw new RuntimeException("unable to RETURN " + getPoolName() + " to pool", e);
        }

        System.err.println("post RETURN " + getPoolName() + " to pool:" + "\tactive count is '" + getActiveCount() + "', idle count is '" + getIdleCount());

    }

    public int getActiveCount() throws UnsupportedOperationException {
        return internalPool.getNumActive();
    }

    public int getIdleCount() throws UnsupportedOperationException {
        return internalPool.getNumIdle();
    }

    public void destroy() throws Exception {
        internalPool.close();
    }

    public synchronized PooledRemoteWebDriver makeObject() {
        System.err.println("CREATING " + getPoolName() + " to pool:" + "\tactive count is '" + getActiveCount() + "', idle count is '" + getIdleCount());

        DesiredCapabilities capability = null;
        if (ComparisonConstants.BROWSER.equalsIgnoreCase("firefox")) {
            System.out.println("firefox");
            capability = DesiredCapabilities.firefox();
            capability.setBrowserName("firefox");
            capability.setPlatform(org.openqa.selenium.Platform.ANY);
            // capability.setVersion("");
        } else if (ComparisonConstants.BROWSER.equalsIgnoreCase("iexplore")) {
            System.out.println("iexplore");
            capability = DesiredCapabilities.internetExplorer();
            capability.setBrowserName("iexplore");
            capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
            // capability.setVersion("");
        } else if (ComparisonConstants.BROWSER.equalsIgnoreCase("chrome")) {
            capability = DesiredCapabilities.chrome();
            capability.setBrowserName("chrome");
            capability.setPlatform(org.openqa.selenium.Platform.ANY);
        }

        try {
            PooledRemoteWebDriver driver = new PooledRemoteWebDriver();
            driver.setRemoteWebdriver(new RemoteWebDriver(new URL(hubUrl), capability));
            return driver;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPoolName() {
        return "PooledRemoteWebDriver";
    }

    public void destroyObject(Object session) throws Exception {
        session = null;
    }

    public void activateObject(Object session) throws Exception {
    }


    public void passivateObject(Object session) throws Exception {
    }


    public boolean validateObject(Object session) {
        return false;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public long getWhenExhaustedAction() {
        return whenExhaustedAction;
    }

    public void setWhenExhaustedAction(long whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
