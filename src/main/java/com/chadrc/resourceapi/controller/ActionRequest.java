package com.chadrc.resourceapi.controller;

import java.util.ArrayList;
import java.util.List;

class ActionRequest {
    private String actionName = null;
    private String resourceName = null;
    private String resourceId = null;
    private List<FieldValue> arguments = new ArrayList<>();

    public ActionRequest() {

    }

    ActionRequest(String actionName, String resourceName, String resourceId, List<FieldValue> arguments) {
        this.actionName = actionName;
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.arguments = arguments;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setArguments(List<FieldValue> arguments) {
        this.arguments = arguments;
    }

    String getActionName() {
        return actionName;
    }

    String getResourceName() {
        return resourceName;
    }

    String getResourceId() {
        return resourceId;
    }

    List<FieldValue> getArguments() {
        return arguments;
    }
}
