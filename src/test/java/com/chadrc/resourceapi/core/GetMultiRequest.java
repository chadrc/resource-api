package com.chadrc.resourceapi.core;

public class GetMultiRequest {
    private String[] ids;

    public GetMultiRequest() {

    }

    public GetMultiRequest(String resourceName, String[] ids) {
        this.ids = ids;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
