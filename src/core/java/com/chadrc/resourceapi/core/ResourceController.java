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

    @GetMapping(path = "/*")
    public ResponseEntity<Result> get(@RequestParam String data, HttpServletRequest servletRequest) {
        return getResponseForMethod(HttpMethod.GET, data, servletRequest);
    }

    @PostMapping
    public ResponseEntity<Result> post(@RequestBody String body, HttpServletRequest servletRequest) {
        return getResponseForMethod(HttpMethod.POST, body, servletRequest);
    }

    @PutMapping
    public ResponseEntity<Result> put(HttpServletRequest servletRequest) {
        return getResponseForMethod(HttpMethod.PUT, null, servletRequest);
    }

    @PatchMapping
    public ResponseEntity<Result> patch(HttpServletRequest servletRequest) {
        return getResponseForMethod(HttpMethod.PATCH, null, servletRequest);
    }

    @DeleteMapping
    public ResponseEntity<Result> delete(HttpServletRequest servletRequest) {
        return getResponseForMethod(HttpMethod.DELETE, null, servletRequest);
    }

    private ResponseEntity<Result> getResponseForMethod(HttpMethod method, String requestObject, HttpServletRequest servletRequest) {
        Map<String, ServiceInfo> serviceInfoPaths = serviceInfoMap.get(method);
        if (serviceInfoPaths == null) {
            return ResponseEntity.status(405).body(null);
        }
        ServiceInfo serviceInfo = serviceInfoPaths.get(servletRequest.getRequestURI());
        if (serviceInfo == null) {
            return ResponseEntity.status(405).body(null);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            Object requestData = null;
            if (!StringUtils.isEmpty(requestObject)) {
                requestData = mapper.readValue(requestObject, serviceInfo.requestClass);
            }
            return ResponseEntity.ok(serviceInfo.resourceService.fulfill(requestData));
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
