package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PatchResourceService;
import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;

public class MockPatchResourceService implements PatchResourceService<PatchRequest> {

    @Override
    public Object fulfill(String resourceName, PatchRequest request) throws ResourceServiceThrowable {
        return new DataResponse("Patch Result");
    }
}
