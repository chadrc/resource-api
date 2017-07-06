package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

public interface PostResourceService<T> extends ResourceService<T> {
    @Override
    default RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
