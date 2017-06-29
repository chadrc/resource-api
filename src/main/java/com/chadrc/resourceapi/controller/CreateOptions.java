package com.chadrc.resourceapi.controller;

import java.util.ArrayList;
import java.util.List;

class CreateOptions {
    private String resourceName = null;
    private List<FieldValue> arguments = new ArrayList<>();

    public CreateOptions() {

    }

    CreateOptions(String resourceName, List<FieldValue> arguments) {
        this.resourceName = resourceName;
        this.arguments = arguments;
    }

    public String getResourceName() {
        return resourceName;
    }

    public List<FieldValue> getArguments() {
        return arguments;
    }
}
