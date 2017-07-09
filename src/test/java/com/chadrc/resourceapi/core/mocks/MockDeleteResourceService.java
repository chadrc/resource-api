package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.*;

public class MockDeleteResourceService implements DeleteResourceService<DeleteRequest> {
    @Override
    public Result fulfill(String resourceName, DeleteRequest request) throws ResourceServiceThrowable {
        return Resource.result(new DataResponse("Delete Result"));
    }
}
