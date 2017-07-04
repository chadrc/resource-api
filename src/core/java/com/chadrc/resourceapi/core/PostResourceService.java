package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;

public interface PostResourceService extends ResourceService {
    @Override
    default HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }
}
