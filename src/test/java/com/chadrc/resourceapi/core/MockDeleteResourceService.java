package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public class MockDeleteResourceService implements DeleteResourceService<GetRequest> {
    @Override
    public Result fulfill(GetRequest request) {
        return new Result("Delete Result");
    }

    @Override
    public Class getRequestClass() {
        return GetRequest.class;
    }
}
