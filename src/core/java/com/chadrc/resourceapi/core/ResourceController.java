package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
@RequestMapping("/${baseResourceUri:}")
public class ResourceController {
    private static Logger log = Logger.getLogger(ResourceController.class);

    private Map<RequestMethod, Map<String, ServiceInfo>> serviceInfoMap = new HashMap<>();

    @Value("${baseResourceUri}")
    private String baseResourceUri;

    @Autowired
    public void setResourceServiceMap(List<ResourceService> resourceServices) {
        for (ResourceService resourceService : resourceServices) {
            Class c = resourceService.getClass();
            Type onlyInterface = c.getGenericInterfaces()[0];
            Class requestClass = (Class) ((ParameterizedType) onlyInterface).getActualTypeArguments()[0];
            ServiceInfo serviceInfo = new ServiceInfo(resourceService, resourceService.getRequestMethod(), requestClass);
            RequestMapping requestMapping = (RequestMapping) c.getAnnotation(RequestMapping.class);
            Map<String, ServiceInfo> serviceInfoPathMap = serviceInfoMap.computeIfAbsent(
                    resourceService.getRequestMethod(), k -> new HashMap<>()
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

    @RequestMapping(path = {"/{resourceName}", "/{resourceName}/*"})
    public ResponseEntity<Object> resource(@PathVariable String resourceName,
                                           @RequestParam(required = false) String data,
                                           @RequestBody(required = false) String  body,
                                           HttpServletRequest servletRequest) {
        RequestMethod method;
        try {
            method = RequestMethod.valueOf(servletRequest.getMethod());
        } catch (IllegalArgumentException | NullPointerException exception) {
            return ResponseEntity.status(405).build();
        }

        String requestObject = body;
        if (method == RequestMethod.GET || method == RequestMethod.HEAD) {
            requestObject = data;
        }

        Map<String, ServiceInfo> serviceInfoPaths = serviceInfoMap.get(method);
        if (serviceInfoPaths == null) {
            if (method == RequestMethod.HEAD) {
                serviceInfoPaths = serviceInfoMap.get(RequestMethod.GET);
                if (serviceInfoPaths == null) {
                    return ResponseEntity.status(405).build();
                }
            } else {
                return ResponseEntity.status(405).build();
            }
        }
        String path = servletRequest.getRequestURI().replace("/" + resourceName, "");
        if (!StringUtils.isEmpty(baseResourceUri)) {
            path = path.replace("/" + baseResourceUri, "");
        }
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
            return ResponseEntity.badRequest().build();
        } catch (ResourceServiceThrowable throwable) {
            return ResponseEntity.status(throwable.getStatus()).body(throwable.getErrorObject());
        }
    }

    private class ServiceInfo {
        ResourceService resourceService;
        RequestMethod httpMethod;
        Class requestClass;
        RequestMapping requestMapping;

        ServiceInfo(ResourceService resourceService, RequestMethod httpMethod, Class requestClass) {
            this.resourceService = resourceService;
            this.httpMethod = httpMethod;
            this.requestClass = requestClass;
            this.requestMapping = resourceService.getClass().getAnnotation(RequestMapping.class);
        }
    }
}
