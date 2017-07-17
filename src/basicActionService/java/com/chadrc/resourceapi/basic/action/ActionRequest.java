package com.chadrc.resourceapi.basic.action;

import com.chadrc.resourceapi.basic.RequestParameter;

import java.util.ArrayList;
import java.util.List;

public class ActionRequest {
    private String id;
    private String name;
    private List<RequestParameter> parameters = new ArrayList<>();

    public ActionRequest() {

    }

    public ActionRequest(String name) {
        this.name = name;
    }

    public ActionRequest(String name, List<RequestParameter> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public ActionRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ActionRequest(String id, String name, List<RequestParameter> parameters) {
        this.id = id;
        this.name = name;
        this.parameters = parameters;
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

    public List<RequestParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<RequestParameter> parameters) {
        this.parameters = parameters;
    }
}
