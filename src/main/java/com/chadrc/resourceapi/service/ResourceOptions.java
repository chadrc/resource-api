package com.chadrc.resourceapi.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceOptions {
    private List<CreateOption> createOptions = new ArrayList<>();
    private List<ActionOption> actionOptions = new ArrayList<>();
    private Map<String, SchemaType> fields = new HashMap<>();

    ResourceOptions(Class c) {
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            createOptions.add(new CreateOption(constructor));
        }

        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (method.getDeclaringClass() == Object.class) {
                continue;
            }

            if (method.getAnnotation(Action.class) != null) {
                actionOptions.add(new ActionOption(method));
            } else if (method.getName().startsWith("get") && method.getParameters().length == 0) {
                String name = method.getName().replace("get", "");
                name = name.substring(0, 1).toLowerCase() + name.substring(1);
                fields.put(name, new SchemaType(method.getReturnType()));
            }
        }
    }

    public List<CreateOption> getCreateOptions() {
        return createOptions;
    }

    public List<ActionOption> getActionOptions() {
        return actionOptions;
    }

    public Map<String, SchemaType> getFields() {
        return fields;
    }
}
