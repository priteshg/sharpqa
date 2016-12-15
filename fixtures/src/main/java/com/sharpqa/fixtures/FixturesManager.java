package com.sharpqa.fixtures;

import org.apache.commons.collections4.map.LazyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pritesh on 13/12/2016.
 */
public enum FixturesManager {
    FIXTURES_MANAGER;

    private final Map<Class<?>, Fixture> fixturesForCurrentTest = LazyMap.lazyMap(new HashMap(), this::enableFixture);

    private FixturesManager() {
        Runtime.getRuntime().addShutdownHook(onShutdown());
    }

    @SuppressWarnings("unchecked")
    public <T> T getFixture(Class<T> clazz) {
        return (T) fixturesForCurrentTest.get(clazz);
    }

    public void clearFixtures() {
        this.fixturesForCurrentTest.values().forEach(x -> x.after());
        this.fixturesForCurrentTest.values().forEach(x -> close(x));
        this.fixturesForCurrentTest.clear();
    }

    private void close(Fixture x) {
        try {
            x.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Fixture enableFixture(Class<?> clazz) {
        try {
            Fixture newFixture = (Fixture) clazz.newInstance();
            newFixture.before();
            return newFixture;
        } catch (IllegalAccessException | InstantiationException var3) {
            throw new RuntimeException(var3);
        }
    }

    private Thread onShutdown() {
        return new Thread() {
            public void run() {
                clearFixtures();
            }
        };
    }
}
