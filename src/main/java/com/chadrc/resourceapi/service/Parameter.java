package com.chadrc.resourceapi.service;

public class Parameter {

    private final Class type;

    public Parameter(Class type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}
