package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;

public class RepositoryGetResourceService implements GetResourceService<GetRequest> {
    @Override
    public Result fulfill(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        return null;
    }
}
