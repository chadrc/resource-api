package com.chadrc.resourceapi.core;

public class GetRequest {
    private String resourceName;
    private String id;

    public GetRequest() {

    }

    public GetRequest(String resourceName, String id) {
        this.resourceName = resourceName;
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
