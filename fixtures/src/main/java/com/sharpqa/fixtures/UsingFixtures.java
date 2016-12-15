package com.sharpqa.fixtures;


import com.sharpqa.junit.UsesFixturesInJunitTest;
import org.junit.rules.TestRule;

/**
 * Created by Pritesh on 13/12/2016.
 */
public interface UsingFixtures {
    default TestRule callBeforeAndAfterOnFixtures() {
        return new UsesFixturesInJunitTest(this);
    }

    default <T> T fixturesFor(Class<T> clazz) {
        return FixturesManager.FIXTURES_MANAGER.getFixture(clazz);
    }
}
