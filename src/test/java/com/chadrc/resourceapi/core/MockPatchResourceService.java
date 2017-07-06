package com.chadrc.resourceapi.core;

public class MockPatchResourceService implements PatchResourceService<PatchRequest> {

    @Override
    public Result fulfill(String resourceName, PatchRequest request) {
        return new Result("Patch Result");
    }
}
