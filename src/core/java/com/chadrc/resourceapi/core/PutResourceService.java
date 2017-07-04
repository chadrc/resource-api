package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;

public interface PutResourceService<T> extends ResourceService<T> {
    @Override
    default HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
    }
}
