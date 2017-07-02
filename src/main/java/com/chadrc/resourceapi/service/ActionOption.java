package com.chadrc.resourceapi.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ActionOption {
    private List<SchemaType> parameters = new ArrayList<>();
    private String name;

    public ActionOption(Method method) {
        name = method.getName();
        for (Parameter parameter : method.getParameters()) {
            parameters.add(new SchemaType(parameter.getType()));
        }
    }

    public List<SchemaType> getParameters() {
        return parameters;
    }

    public String getName() {
        return name;
    }
}
