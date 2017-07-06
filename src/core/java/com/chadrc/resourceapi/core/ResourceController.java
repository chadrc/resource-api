package com.chadrc.resourceapi.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/")
public class ResourceController {
    private static Logger log = Logger.getLogger(ResourceController.class);

    private Map<HttpMethod, Map<String, ServiceInfo>> serviceInfoMap = new HashMap<>();

    @Autowired
    public void setResourceServiceMap(List<ResourceService> resourceServices) {
        for (ResourceService resourceService : resourceServices) {
            Class c = resourceService.getClass();
            Type onlyInterface = c.getGenericInterfaces()[0];
            Class requestClass = (Class) ((ParameterizedType) onlyInterface).getActualTypeArguments()[0];
            ServiceInfo serviceInfo = new ServiceInfo(resourceService, resourceService.getHttpMethod(), requestClass);
            RequestMapping requestMapping = (RequestMapping) c.getAnnotation(RequestMapping.class);
            Map<String, ServiceInfo> serviceInfoPathMap = serviceInfoMap.computeIfAbsent(
                    resourceService.getHttpMethod(), k -> new HashMap<>()
            );
            String[] paths;
            if (requestMapping == null) {
                paths = new String[]{"/"};
            } else {
                paths = requestMapping.path();
            }
            for (String path : paths) {
                serviceInfoPathMap.put(path, serviceInfo);
            }
        }
    }

    @GetMapping(path = {"/{resourceName}", "/{resourceName}/*"})
    public ResponseEntity<Result> get(@PathVariable String resourceName, @RequestParam String data, HttpServletRequest servletRequest) {
        return getResponseForMethod(resourceName, HttpMethod.GET, data, servletRequest);
    }

    @PostMapping(path = {"/{resourceName}*"})
    public ResponseEntity<Result> post(@PathVariable String resourceName, @RequestBody String body, HttpServletRequest servletRequest) {
        return getResponseForMethod(resourceName, HttpMethod.POST, body, servletRequest);
    }

    @PutMapping(path = {"/{resourceName}*"})
    public ResponseEntity<Result> put(@PathVariable String resourceName, @RequestBody String body, HttpServletRequest servletRequest) {
        return getResponseForMethod(resourceName, HttpMethod.PUT, body, servletRequest);
    }

    @PatchMapping(path = {"/{resourceName}*"})
    public ResponseEntity<Result> patch(@PathVariable String resourceName, @RequestBody String body, HttpServletRequest servletRequest) {
        return getResponseForMethod(resourceName, HttpMethod.PATCH, body, servletRequest);
    }

    @DeleteMapping(path = {"/{resourceName}*"})
    public ResponseEntity<Result> delete(@PathVariable String resourceName, @RequestParam String data, HttpServletRequest servletRequest) {
        return getResponseForMethod(resourceName, HttpMethod.DELETE, data, servletRequest);
    }

    private ResponseEntity<Result> getResponseForMethod(String resourceName, HttpMethod method, String requestObject, HttpServletRequest servletRequest) {
        Map<String, ServiceInfo> serviceInfoPaths = serviceInfoMap.get(method);
        if (serviceInfoPaths == null) {
            return ResponseEntity.status(405).body(null);
        }
        String path = servletRequest.getRequestURI().replace("/" + resourceName, "");
        if (StringUtils.isEmpty(path)) {
            path = "/";
        }
        ServiceInfo serviceInfo = serviceInfoPaths.get(path);
        if (serviceInfo == null) {
            return ResponseEntity.notFound().build();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Object requestData = null;
            if (!StringUtils.isEmpty(requestObject)) {
                requestData = mapper.readValue(requestObject, serviceInfo.requestClass);
            }
            return ResponseEntity.ok(serviceInfo.resourceService.fulfill(resourceName, requestData));
        } catch (IOException exception) {
            log.error("Could not deserialize request data.", exception);
            return ResponseEntity.badRequest().body(null);
        }
    }

    private class ServiceInfo {
        ResourceService resourceService;
        HttpMethod httpMethod;
        Class requestClass;
        RequestMapping requestMapping;

        ServiceInfo(ResourceService resourceService, HttpMethod httpMethod, Class requestClass) {
            this.resourceService = resourceService;
            this.httpMethod = httpMethod;
            this.requestClass = requestClass;
            this.requestMapping = resourceService.getClass().getAnnotation(RequestMapping.class);
        }
    }
}
