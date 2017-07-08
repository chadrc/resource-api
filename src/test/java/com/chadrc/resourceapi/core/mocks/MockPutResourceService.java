package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PutResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;

public class MockPutResourceService implements PutResourceService<PutRequest> {
    @Override
    public Result fulfill(String resourceName, PutRequest request) throws ResourceServiceThrowable {
        return Resource.result(new DataResponse("Put Result"));
    }
}
