package com.chadrc.resourceapi.core.mocks;

public class PutRequest {
    private String info = "";

    public PutRequest() {

    }

    public PutRequest(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
