package com.chadrc.resourceapi.core;

import java.util.Map;

public class PostRequest {
    private Map<String, Object> fieldValues;

    public PostRequest() {

    }

    public PostRequest(Map<String, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Map<String, Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
