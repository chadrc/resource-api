package com.chadrc.resourceapi.basic.crud.get;

public class GetRequest {
    private String id;

    public GetRequest() {

    }

    public GetRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
