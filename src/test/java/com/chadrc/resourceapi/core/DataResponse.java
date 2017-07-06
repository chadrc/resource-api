package com.chadrc.resourceapi.core;

public class DataResponse {
    private Object data;

    public DataResponse(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
