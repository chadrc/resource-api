package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
interface ResourceService<T> {
    Object fulfill(String resourceName, T request);

    HttpMethod getHttpMethod();
}
