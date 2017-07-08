package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PutResourceService;
import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;

public class MockPutResourceService implements PutResourceService<PutRequest> {
    @Override
    public Object fulfill(String resourceName, PutRequest request) throws ResourceServiceThrowable {
        return new DataResponse("Put Result");
    }
}
