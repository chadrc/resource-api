package com.chadrc.resourceapi.core;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class MockPutResourceService implements ResourceService {
    @Override
    public Result fulfill() {
        return new Result("Put Result");
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.PUT;
    }
}
