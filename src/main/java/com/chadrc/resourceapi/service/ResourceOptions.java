package com.chadrc.resourceapi.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ResourceOptions {
    private List<CreateOption> createOptions = new ArrayList<>();
    private List<ActionOption> actionOptions = new ArrayList<>();

    ResourceOptions(Class c) {
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            createOptions.add(new CreateOption(constructor));
        }

        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Action.class) != null) {
                actionOptions.add(new ActionOption(method));
            }
        }
    }

    public List<CreateOption> getCreateOptions() {
        return createOptions;
    }

    public List<ActionOption> getActionOptions() {
        return actionOptions;
    }
}
