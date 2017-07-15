package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.core.utils.ReflectionUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/${baseResourceUri:}")
public class ResourceController {
    private static Logger log = Logger.getLogger(ResourceController.class);

    private ResourceOptions resourceOptions;
    private Map<String, Map<String, ServiceInfo>> serviceInfoMap = new HashMap<>();
    @Value("${baseResourceUri}")
    private String baseResourceUri;

    @Autowired
    public void setResourceOptions(ResourceOptions resourceOptions) {
        this.resourceOptions = resourceOptions;
    }

    @Autowired
    public void setResourceServiceMap(List<ResourceService> resourceServices) {
        for (ResourceService resourceService : resourceServices) {
            Class c = resourceService.getClass();
            Type[] typeArgs = ReflectionUtils.getTypeArgsForTypeFromObject(ResourceService.class, resourceService);
            Class requestClass = (Class) typeArgs[0];
            RequestMapping requestMapping = (RequestMapping) c.getAnnotation(RequestMapping.class);
            ResourceMethod resourceMethod = (ResourceMethod) c.getAnnotation(ResourceMethod.class);
            String requestMethod;
            if (resourceMethod == null) {
                requestMethod = requestMapping.method()[0].toString();
            } else {
                requestMethod = resourceMethod.value();
            }
            ServiceInfo serviceInfo = new ServiceInfo(resourceService, requestMethod, requestClass);
            Map<String, ServiceInfo> serviceInfoPathMap = serviceInfoMap.computeIfAbsent(
                    requestMethod, k -> new HashMap<>()
            );
            String[] paths;
            if (requestMapping == null || requestMapping.path().length == 0) {
                paths = new String[]{"/"};
            } else {
                paths = requestMapping.path();
            }
            for (String path : paths) {
                serviceInfoPathMap.put(path, serviceInfo);
            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.OPTIONS)
    public ResponseEntity<Object> options() {
        return ResponseEntity.ok(resourceOptions.getOptions());
    }

    @RequestMapping(path = {"/{resourceName}", "/{resourceName}/*"})
    @SuppressWarnings("unchecked")
    public ResponseEntity<Object> resource(@PathVariable String resourceName, HttpServletRequest servletRequest) {
        String method;
        try {
            method = servletRequest.getMethod();
        } catch (IllegalArgumentException | NullPointerException exception) {
            return ResponseEntity.status(405).build();
        }

        Map<String, ServiceInfo> serviceInfoPaths = serviceInfoMap.get(method);
        if (serviceInfoPaths == null) {
            if (method.equals(RequestMethod.HEAD.toString())) {
                serviceInfoPaths = serviceInfoMap.get(RequestMethod.GET.toString());
                if (serviceInfoPaths == null) {
                    return ResponseEntity.status(405).build();
                }
            } else {
                return ResponseEntity.status(405).build();
            }
        }

        Class targetResource = resourceOptions.getResource(resourceName);
        if (targetResource == null) {
            return ResponseEntity.notFound().build();
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
            Constructor requestConstructor = null;
            for (Constructor constructor : serviceInfo.requestClass.getConstructors()) {
                if (constructor.getParameterCount() == 0) {
                    requestConstructor = constructor;
                }
            }
            if (requestConstructor == null) {
                throw new RequestTypeMustHaveConstructor();
            }
            Object requestData = null;
            if (servletRequest.getContentLength() >= 0) {
                if (!StringUtils.isEmpty(servletRequest.getContentType())
                        && (servletRequest.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)
                        || servletRequest.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE))) {
                    requestData = mapper.readValue(servletRequest.getInputStream(), serviceInfo.requestClass);
                }
            }

            if (requestData == null) {
                requestData = requestConstructor.newInstance();
            }

            ServletRequestDataBinder dataBinder = new ExtendedServletRequestDataBinder(requestData);
            dataBinder.bind(servletRequest);
            Result result = serviceInfo.resourceService.fulfill(resourceOptions.getResource(resourceName), requestData);
            if (result == null) {
                throw new ServiceMustReturnResultException();
            }
            return ResponseEntity.ok(result.getResult());
        } catch (JsonParseException parseException) {
            return ResponseEntity.badRequest().build();
        } catch (ResourceServiceThrowable throwable) {
            return ResponseEntity.status(throwable.getStatus()).body(throwable.getErrorObject());
        } catch (Exception exception) {
            log.error("Error during request:", exception);
            return ResponseEntity.status(500).build();
        }
    }

    private class ServiceInfo {
        ResourceService resourceService;
        String httpMethod;
        Class requestClass;
        RequestMapping requestMapping;

        ServiceInfo(ResourceService resourceService, String httpMethod, Class requestClass) {
            this.resourceService = resourceService;
            this.httpMethod = httpMethod;
            this.requestClass = requestClass;
            this.requestMapping = resourceService.getClass().getAnnotation(RequestMapping.class);
        }
    }
}
