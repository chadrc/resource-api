package com.chadrc.resourceapi.services;

public class GetResult {
    private Object resource;

    GetResult(Object resource) {
        this.resource = resource;
    }

    public Object getResource() {
        return resource;
    }
}
