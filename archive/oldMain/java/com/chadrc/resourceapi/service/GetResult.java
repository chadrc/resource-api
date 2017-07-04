package com.chadrc.resourceapi.service;

public class GetResult {
    private Object resource;

    GetResult(Object resource) {
        this.resource = resource;
    }

    public Object getResource() {
        return resource;
    }
}
