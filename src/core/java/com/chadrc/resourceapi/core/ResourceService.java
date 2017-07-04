package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public interface ResourceService {
    Result fulfill(GetRequest request);

    HttpMethod getHttpMethod();
}
