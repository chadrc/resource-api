package com.chadrc.resourceapi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResourceOptions {
    private Map<String, Map<String, Object>> options = new HashMap<>();
    private Map<String, Class> resourceModelMap = new HashMap<>();

    @Autowired
    public void setResourceServiceMap(List<ResourceModel> models, List<ResourceOptionsProvider> resourceOptionsProviders) {
        for (ResourceModel resourceModel : models) {
            options.put(convertResourceName(resourceModel), new HashMap<>());
            resourceModelMap.put(convertResourceName(resourceModel), resourceModel.getClass());
        }

        for (ResourceOptionsProvider resourceOptionsProvider : resourceOptionsProviders) {
            for (ResourceModel resourceModel : models) {
                Map<String, Object> serviceOptions = resourceOptionsProvider.getOptions(resourceModel.getClass());
                Map<String, Object> resourceOptions = options.get(convertResourceName(resourceModel));
                for (String key : serviceOptions.keySet()) {
                    resourceOptions.put(key, serviceOptions.get(key));
                }
            }
        }
    }

    private String convertResourceName(Object obj) {
        return obj.getClass().getSimpleName().toLowerCase();
    }

    Map<String, Map<String, Object>> getOptions() {
        return options;
    }

    Class getResource(String resourceName) {
        return resourceModelMap.get(resourceName);
    }
}
