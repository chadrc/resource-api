package com.chadrc.resourceapi.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/")
public class ResourceController {
    private static Logger log = Logger.getLogger(ResourceController.class);

    private Map<HttpMethod, ServiceInfo> serviceInfoMap = new HashMap<>();

    @Autowired
    public void setResourceServiceMap(List<ResourceService> resourceServices) {
        for (ResourceService resourceService : resourceServices) {
            Class c = resourceService.getClass();
            Type onlyInterface = c.getGenericInterfaces()[0];
            Class requestClass = (Class) ((ParameterizedType) onlyInterface).getActualTypeArguments()[0];
            ServiceInfo serviceInfo = new ServiceInfo(resourceService, resourceService.getHttpMethod(), requestClass);
            serviceInfoMap.put(resourceService.getHttpMethod(), serviceInfo);
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
        ServiceInfo serviceInfo = serviceInfoMap.get(method);
        ResourceService resourceService = serviceInfo.resourceService;
        if (resourceService == null) {
            return ResponseEntity.status(405).body(null);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Object requestData = null;
            if (!StringUtils.isEmpty(requestObject)) {
                requestData = mapper.readValue(requestObject, serviceInfo.requestClass);
            }
            return ResponseEntity.ok(resourceService.fulfill(requestData));
        } catch (IOException exception) {
            log.error("Could not deserialize request data.", exception);
            return ResponseEntity.badRequest().body(null);
        }
    }

    private class ServiceInfo {
        ResourceService resourceService;
        HttpMethod httpMethod;
        Class requestClass;

        ServiceInfo(ResourceService resourceService, HttpMethod httpMethod, Class requestClass) {
            this.resourceService = resourceService;
            this.httpMethod = httpMethod;
            this.requestClass = requestClass;
        }
    }
}
