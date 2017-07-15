package com.chadrc.resourceapi.basic.crud.update;

import java.util.Map;

public class PatchRequest {
    private String id;
    private Map<String, Object> fields;

    public PatchRequest() {

    }

    public PatchRequest(String id, Map<String, Object> fields) {
        this.id = id;
        this.fields = fields;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
