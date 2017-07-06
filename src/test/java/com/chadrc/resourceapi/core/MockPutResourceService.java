package com.chadrc.resourceapi.core;

public class MockPutResourceService implements PutResourceService<PutRequest> {
    @Override
    public Result fulfill(String resourceName, PutRequest request) {
        return new Result("Put Result");
    }
}
