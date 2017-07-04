package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class MockDeleteResourceService implements ResourceService {
    @Override
    public Result fulfill(GetRequest request) {
        return new Result("Delete Result");
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.DELETE;
    }
}
