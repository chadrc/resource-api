package com.chadrc.resourceapi.core;

public class Result {
    private Object data;

    public Result(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
