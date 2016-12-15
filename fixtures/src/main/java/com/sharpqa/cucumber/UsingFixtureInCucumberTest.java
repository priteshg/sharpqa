package com.sharpqa.cucumber;


import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import static com.sharpqa.fixtures.FixturesManager.FIXTURES_MANAGER;


/**
 * Created by Pritesh on 15/12/2016.
 */
public class UsingFixtureInCucumberTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsingFixtureInCucumberTest.class);

    @Before(order = Integer.MAX_VALUE)
    public void beforeScenario(Scenario scenario) {
        LOGGER.info("Running " + scenario.getName());
    }

    @After(order = Integer.MAX_VALUE)
    public void afterScenario() {
        FIXTURES_MANAGER.clearFixtures();
    }

}
