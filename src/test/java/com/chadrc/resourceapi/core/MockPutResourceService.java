package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public class MockPutResourceService implements PutResourceService<PutRequest> {
    @Override
    public Result fulfill(PutRequest request) {
        return new Result("Put Result");
    }
}
