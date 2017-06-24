package com.chadrc.resourceapi.services;

import java.util.Map;

public class OptionsResult {

    private Map<String, Object> options;

    OptionsResult(Map<String, Object> options) {
        this.options = options;
    }

    public Map<String, Object> getOptions() {
        return options;
    }
}
