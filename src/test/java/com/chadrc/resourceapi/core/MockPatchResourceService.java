package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public class MockPatchResourceService implements PatchResourceService {

    @Override
    public Result fulfill(GetRequest request) {
        return new Result("Patch Result");
    }
}
