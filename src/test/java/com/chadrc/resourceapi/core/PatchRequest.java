package com.chadrc.resourceapi.core;

public class PatchRequest {
    private String resourceName;

    public PatchRequest() {

    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
