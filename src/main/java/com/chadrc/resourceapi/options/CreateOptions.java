package com.chadrc.resourceapi.options;

import java.util.ArrayList;
import java.util.List;

public class CreateOptions {
    private String resourceName = null;
    private List<FieldValue> arguments = new ArrayList<>();

    public String getResourceName() {
        return resourceName;
    }

    public List<FieldValue> getArguments() {
        return arguments;
    }
}
