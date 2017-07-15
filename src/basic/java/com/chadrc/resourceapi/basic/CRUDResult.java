package com.chadrc.resourceapi.basic;

public class CRUDResult {
    private Object data;

    public CRUDResult(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
