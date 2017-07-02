package com.chadrc.resourceapi.service;

import java.util.Map;

public class OptionsResult {

    private Map<String, ResourceOptions> options;

    OptionsResult(Map<String, ResourceOptions> options) {
        this.options = options;
    }

    public Map<String, ResourceOptions> getOptions() {
        return options;
    }
}
