package com.chadrc.resourceapi.basic.crud;

import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public class RepositoryGetResourceService implements GetResourceService<GetRequest> {

    private List<ResourceRepository> resourceRepositories;

    @Autowired
    public void setResourceRepositories(List<ResourceRepository> resourceRepositories) {
        this.resourceRepositories = resourceRepositories;
    }

    @Override
    public Result fulfill(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        return Resource.result(resourceRepositories.get(0).findOne(request.getIds().get(0)));
    }
}
