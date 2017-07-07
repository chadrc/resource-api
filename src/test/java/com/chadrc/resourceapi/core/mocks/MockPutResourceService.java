package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PutResourceService;

public class MockPutResourceService implements PutResourceService<PutRequest> {
    @Override
    public Object fulfill(String resourceName, PutRequest request) {
        return new DataResponse("Put Result");
    }
}
