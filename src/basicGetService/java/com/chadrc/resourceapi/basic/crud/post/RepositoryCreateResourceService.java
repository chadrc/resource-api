package com.chadrc.resourceapi.basic.crud.post;

import com.chadrc.resourceapi.core.PostResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;

public class RepositoryCreateResourceService implements PostResourceService<CreateRequest> {
    @Override
    public Result fulfill(Class resourceType, CreateRequest request) throws ResourceServiceThrowable {
        return null;
    }
}
