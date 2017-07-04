package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;

public interface PutResourceService extends ResourceService {
    @Override
    default HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
    }
}
