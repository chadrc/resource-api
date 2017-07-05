package com.chadrc.resourceapi.core;

import org.springframework.stereotype.Service;

public class MockDeleteResourceService implements DeleteResourceService<DeleteRequest> {
    @Override
    public Result fulfill(DeleteRequest request) {
        return new Result("Delete Result");
    }
}
