package com.chadrc.resourceapi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResourceOptions {
    private Map<String, Map<String, Object>> options = new HashMap<>();
    private List<ResourceModel> resourceModels = new ArrayList<>();

    @Autowired
    public void setResourceServiceMap(List<ResourceModel> models, List<ResourceOptionsProvider> resourceOptionsProviders) {
        this.resourceModels = models;

        for (ResourceModel resourceModel : resourceModels) {
            options.put(resourceModel.getClass().getSimpleName().toLowerCase(), new HashMap<>());
        }

        for (ResourceOptionsProvider resourceOptionsProvider : resourceOptionsProviders) {
            for (ResourceModel resourceModel : resourceModels) {
                Map<String, Object> serviceOptions = resourceOptionsProvider.getOptions(resourceModel.getClass());
                Map<String, Object> resourceOptions = options.get(resourceModel.getClass().getSimpleName().toLowerCase());
                for (String key : serviceOptions.keySet()) {
                    resourceOptions.put(key, serviceOptions.get(key));
                }
            }
        }
    }

    Map<String, Map<String, Object>> getOptions() {
        return options;
    }
}
