package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;

public class RepositoryGetResourceSerivce implements GetResourceService<GetRequest> {
    @Override
    public Result fulfill(String resourceName, GetRequest request) throws ResourceServiceThrowable {
        return null;
    }
}
