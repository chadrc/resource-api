package com.chadrc.resourceapi.core.mocks;

import com.chadrc.resourceapi.core.DeleteResourceService;
import com.chadrc.resourceapi.core.exceptions.ResourceServiceThrowable;

public class MockDeleteResourceService implements DeleteResourceService<DeleteRequest> {
    @Override
    public Object fulfill(String resourceName, DeleteRequest request) throws ResourceServiceThrowable {
        return new DataResponse("Delete Result");
    }
}
