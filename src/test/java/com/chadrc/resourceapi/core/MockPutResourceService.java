package com.chadrc.resourceapi.core;

public class MockPutResourceService implements PutResourceService<PutRequest> {
    @Override
    public Object fulfill(String resourceName, PutRequest request) {
        return new DataResponse("Put Result");
    }
}
