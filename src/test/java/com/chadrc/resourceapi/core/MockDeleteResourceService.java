package com.chadrc.resourceapi.core;

public class MockDeleteResourceService implements DeleteResourceService<DeleteRequest> {
    @Override
    public Result fulfill(String resourceName, DeleteRequest request) {
        return new Result("Delete Result");
    }
}
