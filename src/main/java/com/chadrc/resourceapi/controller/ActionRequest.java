package com.chadrc.resourceapi.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class ActionRequest {
    private String actionName = null;
    private String resourceName = null;
    private String resourceId = null;
    private List<FieldValue> arguments = new ArrayList<>();

    ActionRequest(String actionName, String resourceName, String resourceId, List<FieldValue> arguments) {
        this.actionName = actionName;
        this.resourceName = resourceName;
        this.resourceId = resourceId;
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
