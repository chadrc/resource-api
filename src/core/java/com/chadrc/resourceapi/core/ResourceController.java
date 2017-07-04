package com.chadrc.resourceapi.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<Result> get(@RequestParam String request) {
        return getResponseForMethod(HttpMethod.GET, request);
    }

    @PostMapping
    public ResponseEntity<Result> post(@RequestBody String body) {
        return getResponseForMethod(HttpMethod.POST, body);
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

    private ResponseEntity<Result> getResponseForMethod(HttpMethod method, String requestObject) {
        ResourceService resourceService = resourceServiceMap.get(method);
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Object requestData = null;
            if (!StringUtils.isEmpty(requestObject)) {
                requestData = mapper.readValue(requestObject, resourceService.getRequestClass());
            }
            return ResponseEntity.ok(resourceService.fulfill(requestData));
        } catch (IOException exception) {
            log.error("Could not deserialize request data.", exception);
            return ResponseEntity.badRequest().body(null);
        }
    }
}
