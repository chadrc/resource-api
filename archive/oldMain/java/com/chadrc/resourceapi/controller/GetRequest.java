package com.chadrc.resourceapi.controller;

class GetRequest {
    private String resourceName = null;
    private String id = null;

    public GetRequest() {

    }

    GetRequest(String resourceName, String id) {
        this.resourceName = resourceName;
        this.id = id;
    }

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
