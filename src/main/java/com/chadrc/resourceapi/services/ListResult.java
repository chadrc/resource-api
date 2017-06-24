package com.chadrc.resourceapi.services;

public class ListResult {
    private ResourcePage resourceList;

    ListResult(ResourcePage resourceList) {
        this.resourceList = resourceList;
    }

    public ResourcePage getResourcePage() {
        return resourceList;
    }
}
