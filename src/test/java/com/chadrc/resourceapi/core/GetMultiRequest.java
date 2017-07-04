package com.chadrc.resourceapi.core;

public class GetMultiRequest {
    private String resourceName;
    private String[] ids;

    public GetMultiRequest() {

    }

    public GetMultiRequest(String resourceName, String[] ids) {
        this.resourceName = resourceName;
        this.ids = ids;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
