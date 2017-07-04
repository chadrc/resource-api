package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public class MockPatchResourceService implements PatchResourceService<PatchRequest> {

    @Override
    public Result fulfill(PatchRequest request) {
        return new Result("Patch Result");
    }
}
