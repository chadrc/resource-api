package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
interface ResourceService<T> {
    Result fulfill(String resourceName, T request);

    HttpMethod getHttpMethod();
}
