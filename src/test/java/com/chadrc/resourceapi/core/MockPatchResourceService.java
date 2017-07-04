package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

@Service
public class MockPatchResourceService implements PatchResourceService<GetRequest> {

    @Override
    public Result fulfill(GetRequest request) {
        return new Result("Patch Result");
    }

    @Override
    public Class getRequestClass() {
        return GetRequest.class;
    }
}
