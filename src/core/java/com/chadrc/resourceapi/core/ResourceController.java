package com.chadrc.resourceapi.core;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return getResponseForMethod(HttpMethod.GET);
    }

    @PostMapping
    public ResponseEntity<Result> post() {
        return getResponseForMethod(HttpMethod.POST);
    }

    @PutMapping
    public ResponseEntity<Result> put() {
        return getResponseForMethod(HttpMethod.PUT);
    }

    @PatchMapping
    public ResponseEntity<Result> patch() {
        return getResponseForMethod(HttpMethod.PATCH);
    }

    @DeleteMapping
    public ResponseEntity<Result> delete() {
        return getResponseForMethod(HttpMethod.DELETE);
    }

    private ResponseEntity<Result> getResponseForMethod(HttpMethod method) {
        ResourceService resourceService = resourceServiceMap.get(method);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }
        return ResponseEntity.ok(resourceService.fulfill());
    }
}
