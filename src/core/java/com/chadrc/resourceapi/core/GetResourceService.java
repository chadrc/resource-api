package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;

public interface GetResourceService<T> extends ResourceService<T> {
    @Override
    default HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }
}
