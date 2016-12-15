package com.sharpqa.junit;

import com.sharpqa.fixtures.UsingFixtures;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by Pritesh on 13/12/2016.
 */
public class UsesFixturesInJunitTest implements TestRule {

    public UsesFixturesInJunitTest(UsingFixtures usesFixture) {
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new CallAfterOnFixture(base, description);
    }
}
