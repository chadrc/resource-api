package com.chadrc.resourceapi.core;

public class PostRequest {
    private String value = "Post";

    public PostRequest() {

    }

    public PostRequest(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
