package com.chadrc.resourceapi.core;

public class MockDeleteResourceService implements DeleteResourceService<DeleteRequest> {
    @Override
    public Object fulfill(String resourceName, DeleteRequest request) {
        return new DataResponse("Delete Result");
    }
}
