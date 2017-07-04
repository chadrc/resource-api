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
    public ResponseEntity<Result> get(GetRequest request) {
        return getResponseForMethod(HttpMethod.GET, request);
    }

    @PostMapping
    public ResponseEntity<Result> post() {
        return getResponseForMethod(HttpMethod.POST, null);
    }

    @PutMapping
    public ResponseEntity<Result> put() {
        return getResponseForMethod(HttpMethod.PUT, null);
    }

    @PatchMapping
    public ResponseEntity<Result> patch() {
        return getResponseForMethod(HttpMethod.PATCH, null);
    }

    @DeleteMapping
    public ResponseEntity<Result> delete() {
        return getResponseForMethod(HttpMethod.DELETE, null);
    }

    private ResponseEntity<Result> getResponseForMethod(HttpMethod method, GetRequest request) {
        ResourceService resourceService = resourceServiceMap.get(method);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }
        return ResponseEntity.ok(resourceService.fulfill(request));
    }
}
