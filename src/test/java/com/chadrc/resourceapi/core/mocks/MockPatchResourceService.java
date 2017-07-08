package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PatchResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.Result;
import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;

public class MockPatchResourceService implements PatchResourceService<PatchRequest> {

    @Override
    public Result fulfill(String resourceName, PatchRequest request) throws ResourceServiceThrowable {
        return Resource.result(new DataResponse("Patch Result"));
    }
}
