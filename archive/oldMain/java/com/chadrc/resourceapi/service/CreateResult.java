package com.chadrc.resourceapi.service;

public class CreateResult {

    private Object createdResource;

    CreateResult(Object createdResource) {
        this.createdResource = createdResource;
    }

    public Object getCreatedResource() {
        return createdResource;
    }
}
