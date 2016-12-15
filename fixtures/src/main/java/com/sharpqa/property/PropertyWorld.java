package com.sharpqa.property;

import com.sharpqa.fixtures.Fixture;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Pritesh on 15/12/2016.
 */
public class PropertyWorld implements Fixture {

    private ThreadLocal<HashMap<String, Object>> properties = new ThreadLocal<HashMap<String, Object>>() {
        protected HashMap<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    public HashMap<String, Object> properties() {
        return properties.get();
    }

    public Object getPropertty(String key) {
        return properties().get(key);
    }

    public void setProperties(String key, Object value) {
        properties().put(key, value);
    }

    public boolean hasPropertyKey(String key) {
        return properties().containsKey(key);
    }

    @Override
    public void before() {
        properties.remove();
    }

}
