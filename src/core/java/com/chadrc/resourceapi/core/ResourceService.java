package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
interface ResourceService {
    Result fulfill(GetRequest request);

    HttpMethod getHttpMethod();
}
