package com.chadrc.resourceapi.basic.action;

public class ActionRequest {
    private String id;
    private String name;

    public ActionRequest() {

    }

    public ActionRequest(String name) {
        this.name = name;
    }

    public ActionRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
