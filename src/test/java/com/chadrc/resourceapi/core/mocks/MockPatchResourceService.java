package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.PatchResourceService;

public class MockPatchResourceService implements PatchResourceService<PatchRequest> {

    @Override
    public Object fulfill(String resourceName, PatchRequest request) {
        return new DataResponse("Patch Result");
    }
}
