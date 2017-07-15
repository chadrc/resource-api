package com.chadrc.resourceapi.basic.crud.get;

import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RepositoryGetResourceService implements GetResourceService<GetRequest> {

    private List<ResourceRepository> resourceRepositories;

    @Autowired
    public void setResourceRepositories(List<ResourceRepository> resourceRepositories) {
        this.resourceRepositories = resourceRepositories;
    }

    @Override
    public Result fulfill(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        Object resource = resourceRepositories.get(0).findOne(request.getId());
        if (resource == null) {
            throw Resource.notFound();
        }
        return Resource.result(resource);
    }
}
