package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;

public interface DeleteResourceService extends ResourceService {
    @Override
    default HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }
}
