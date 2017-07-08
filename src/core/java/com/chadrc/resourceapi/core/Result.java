package com.chadrc.resourceapi.core;

public class Result {
    private Object result;

    Result(Object result) {
        this.result = result;
    }

    Object getResult() {
        return result;
    }
}
