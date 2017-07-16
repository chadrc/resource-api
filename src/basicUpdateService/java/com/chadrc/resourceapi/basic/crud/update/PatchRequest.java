package com.chadrc.resourceapi.basic.crud.update;

import com.chadrc.resourceapi.basic.RequestParameter;

import java.util.Map;

public class PatchRequest {
    private String id;
    private Map<String, RequestParameter> fields;

    public PatchRequest() {

    }

    public PatchRequest(String id, Map<String, RequestParameter> fields) {
        this.id = id;
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, RequestParameter> getFields() {
        return fields;
    }

    public void setFields(Map<String, RequestParameter> fields) {
        this.fields = fields;
    }
}
