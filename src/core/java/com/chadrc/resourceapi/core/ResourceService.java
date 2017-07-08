package com.chadrc.resourceapi.core;

import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
interface ResourceService<T> {
    Result fulfill(String resourceName, T request) throws ResourceServiceThrowable;

    RequestMethod getRequestMethod();
}
