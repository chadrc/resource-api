package com.chadrc.resourceapi.core;

public class MockGetResourceService implements ResourceService {
    @Override
    public Result fulfill() {
        return new Result("Get Result");
    }
}
