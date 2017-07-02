package com.chadrc.resourceapi.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CreateOption {

    private List<Parameter> parameters = new ArrayList<>();

    CreateOption(Constructor constructor) {
        Type[] params = constructor.getParameterTypes();
        for (Type type : params) {
            parameters.add(new Parameter((Class) type));
        }
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}
