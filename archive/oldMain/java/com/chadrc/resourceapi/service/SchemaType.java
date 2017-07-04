package com.chadrc.resourceapi.service;

public class SchemaType {

    private final Class type;

    public SchemaType(Class type) {
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}
