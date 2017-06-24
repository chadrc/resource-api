package com.chadrc.resourceapi.options;

class GetOptions {
    private String resourceName = null;
    private String id = null;

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getResourceName() {
        return resourceName;
    }

    String getId() {
        return id;
    }
}
