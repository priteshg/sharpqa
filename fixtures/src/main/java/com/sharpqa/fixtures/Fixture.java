package com.sharpqa.fixtures;

/**
 * Created by Pritesh on 13/12/2016.
 */

public interface Fixture extends AutoCloseable {

    default void before() {}

    default void after() {
    }

    default void close() throws Exception {
    }

    default boolean isEnabled() {
        return true;
    }
}
