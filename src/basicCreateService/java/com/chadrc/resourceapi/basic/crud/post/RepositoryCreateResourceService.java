package com.chadrc.resourceapi.basic.crud.post;

import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(method = RequestMethod.POST)
public class RepositoryCreateResourceService implements ResourceService<CreateRequest> {
    @Override
    public Result fulfill(Class resourceType, CreateRequest request) throws ResourceServiceThrowable {
        return null;
    }
}
