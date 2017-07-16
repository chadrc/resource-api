package com.chadrc.resourceapi.basic;

public class RequestParameter {
    private String name;
    private Object value;

    public RequestParameter() {

    }

    public RequestParameter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
