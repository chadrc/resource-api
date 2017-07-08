package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PutResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;

public class MockPutResourceService implements PutResourceService<PutRequest> {
    @Override
    public Result fulfill(String resourceName, PutRequest request) throws ResourceServiceThrowable {
        if ("return null".equals(request.getInfo())) {
            return null;
        }
        return Resource.result(new DataResponse("Put Result"));
    }
}
