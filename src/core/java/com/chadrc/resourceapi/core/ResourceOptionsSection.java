package com.chadrc.resourceapi.core;

import java.util.HashMap;
import java.util.Map;

public class ResourceOptionsSection {
    private Map<String, Object> options = new HashMap<>();

    ResourceOptionsSection() {

    }

    public ResourceOptionsSection add(String key, Object value) {
        options.put(key, value);
        return this;
    }

    Map<String, Object> getOptions() {
        return options;
    }
}
