package com.chadrc.resourceapi.basic.crud.get;

import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(method = RequestMethod.GET)
public class RepositoryGetResourceService implements ResourceService<GetRequest> {

    private ResourceRepositorySet resourceRepositorySet;

    @Autowired
    public void setResourceRepositorySet(ResourceRepositorySet resourceRepositorySet) {
        this.resourceRepositorySet = resourceRepositorySet;
    }

    @Override
    public Result fulfill(Class resourceType, GetRequest request) throws ResourceServiceThrowable {
        Object resource = resourceRepositorySet.getRepository(resourceType).findOne(request.getId());
        if (resource == null) {
            throw Resource.notFound();
        }
        return Resource.result(resource);
    }
}
