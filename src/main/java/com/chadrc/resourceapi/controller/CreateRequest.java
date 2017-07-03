package com.chadrc.resourceapi.controller;

import java.util.ArrayList;
import java.util.List;

class CreateRequest {
    private String resourceName = null;
    private List<FieldValue> arguments = new ArrayList<>();

    public CreateRequest() {

    }

    CreateRequest(String resourceName, List<FieldValue> arguments) {
        this.resourceName = resourceName;
        this.arguments = arguments;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setArguments(List<FieldValue> arguments) {
        this.arguments = arguments;
    }

    String getResourceName() {
        return resourceName;
    }

    List<FieldValue> getArguments() {
        return arguments;
    }

    void setDefaults() {
        if (arguments == null) {
            arguments = new ArrayList<>();
        }
    }
}
