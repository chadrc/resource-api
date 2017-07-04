package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public class MockPutResourceService implements PutResourceService<GetRequest> {
    @Override
    public Result fulfill(GetRequest request) {
        return new Result("Put Result");
    }
}
