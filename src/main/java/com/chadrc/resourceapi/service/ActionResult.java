package com.chadrc.resourceapi.service;

public class ActionResult {
    private Object result;

    ActionResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
