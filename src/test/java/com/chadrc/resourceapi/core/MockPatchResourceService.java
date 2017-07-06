package com.chadrc.resourceapi.core;

public class MockPatchResourceService implements PatchResourceService<PatchRequest> {

    @Override
    public Object fulfill(String resourceName, PatchRequest request) {
        return new DataResponse("Patch Result");
    }
}
