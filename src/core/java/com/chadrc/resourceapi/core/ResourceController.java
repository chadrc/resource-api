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

    @PutMapping
    public ResponseEntity<Result> put() {
        ResourceService resourceService = resourceServiceMap.get(HttpMethod.PUT);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }
        return ResponseEntity.ok(resourceService.fulfill());
    }

    @PatchMapping
    public ResponseEntity<Result> patch() {
        ResourceService resourceService = resourceServiceMap.get(HttpMethod.PATCH);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }
        return ResponseEntity.ok(resourceService.fulfill());
    }

    @DeleteMapping
    public ResponseEntity<Result> delete() {
        ResourceService resourceService = resourceServiceMap.get(HttpMethod.DELETE);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }
        return ResponseEntity.ok(resourceService.fulfill());
    }
}
