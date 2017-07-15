package com.chadrc.resourceapi.basic.crud.get;

import com.chadrc.resourceapi.basic.ResourceRepository;
import com.chadrc.resourceapi.basic.ResourceRepositorySet;
import com.chadrc.resourceapi.core.GetResourceService;
import com.chadrc.resourceapi.core.Resource;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryGetResourceService implements GetResourceService<GetRequest> {

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
