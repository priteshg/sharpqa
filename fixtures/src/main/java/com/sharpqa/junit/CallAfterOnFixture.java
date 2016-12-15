package com.sharpqa.junit;


import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.sharpqa.fixtures.FixturesManager.FIXTURES_MANAGER;

/**
 * Created by Pritesh on 15/12/2016.
 */
public class CallAfterOnFixture extends Statement {


    private Statement statement;

    public CallAfterOnFixture(Statement base, Description description) {
        this.statement = base;
    }

    @Override
    public void evaluate() throws Throwable {
        try {
            statement.evaluate();
        } finally {
            FIXTURES_MANAGER.clearFixtures();
        }
    }
}
