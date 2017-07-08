package com.chadrc.resourceapi.core.mocks;

public class PatchRequest {
    private String info = "";

    public PatchRequest() {

    }

    public PatchRequest(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
