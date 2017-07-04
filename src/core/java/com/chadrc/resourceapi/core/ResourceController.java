package com.chadrc.resourceapi.core;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/")
public class ResourceController {
    private static Logger log = Logger.getLogger(ResourceController.class);

    private Map<HttpMethod, ResourceService> resourceServiceMap = new HashMap<>();

    @Autowired
    public void setResourceServiceMap(List<ResourceService> resourceServices) {
        for (ResourceService resourceService : resourceServices) {
            resourceServiceMap.put(resourceService.getHttpMethod(), resourceService);
        }
    }

    @GetMapping
    public ResponseEntity<Result> get() {
        ResourceService resourceService = resourceServiceMap.get(HttpMethod.GET);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }
        return ResponseEntity.ok(resourceService.fulfill());
    }

    @PostMapping
    public ResponseEntity<Result> post() {
        ResourceService resourceService = resourceServiceMap.get(HttpMethod.POST);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }
        return ResponseEntity.ok(resourceService.fulfill());
    }
}
