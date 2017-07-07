package com.chadrc.resourceapi.core.mocks;

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
