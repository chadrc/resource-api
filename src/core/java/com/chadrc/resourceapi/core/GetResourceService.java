package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;

public interface GetResourceService extends ResourceService {
    @Override
    default HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }
}
