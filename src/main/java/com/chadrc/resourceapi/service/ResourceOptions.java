package com.chadrc.resourceapi.service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ResourceOptions {
    private List<CreateOption> createOptions = new ArrayList<>();

    public ResourceOptions(Class c) {
        Constructor[] constructors = c.getConstructors();
        for (Constructor constructor : constructors) {
            createOptions.add(new CreateOption());
        }
    }

    public List<CreateOption> getCreateOptions() {
        return createOptions;
    }
}
