package com.chadrc.resourceapi.core;

import java.util.Map;

public class PostRequest {
    private String resourceName;
    private Map<String, Object> fieldValues;

    public PostRequest() {

    }

    public PostRequest(String resourceName, Map<String, Object> fieldValues) {
        this.resourceName = resourceName;
        this.fieldValues = fieldValues;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Map<String, Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
