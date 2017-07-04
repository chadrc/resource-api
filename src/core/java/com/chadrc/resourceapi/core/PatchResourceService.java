package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;

public interface PatchResourceService extends ResourceService {
    @Override
    default HttpMethod getHttpMethod() {
        return HttpMethod.PATCH;
    }
}
