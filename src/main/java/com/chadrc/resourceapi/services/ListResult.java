package com.chadrc.resourceapi.services;

public class ListResult {
    private Object resourceList;

    ListResult(Object resourceList) {
        this.resourceList = resourceList;
    }

    public Object getResourceList() {
        return resourceList;
    }
}
