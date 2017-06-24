package com.chadrc.resourceapi.options;

import java.util.ArrayList;
import java.util.List;

class ActionOptions {
    private String actionName = null;
    private String resourceName = null;
    private String resourceId = null;
    private List<FieldValue> arguments = new ArrayList<>();

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
