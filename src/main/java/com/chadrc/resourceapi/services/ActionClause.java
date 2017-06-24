package com.chadrc.resourceapi.services;

import com.chadrc.resourceapi.options.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class ActionClause {
    private String actionName;
    private String resourceName;
    private String resourceId;
    private List<FieldValue> arguments = new ArrayList<>();

    public ActionClause(String actionName, String resourceName, String resourceId, List<FieldValue> arguments) {
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
