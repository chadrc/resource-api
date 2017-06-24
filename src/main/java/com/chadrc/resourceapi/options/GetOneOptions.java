package com.chadrc.resourceapi.options;

public class GetOneOptions {
    private String resourceName = null;
    private String id = null;

    GetOneOptions(String resourceName, String id) {
        this.resourceName = resourceName;
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getId() {
        return id;
    }
}
