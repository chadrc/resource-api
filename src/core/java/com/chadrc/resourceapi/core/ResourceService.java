package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
public interface ResourceService<T> {
    Result fulfill(String resourceName, T request) throws ResourceServiceThrowable;

    RequestMethod getRequestMethod();
}
