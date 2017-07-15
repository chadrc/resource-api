package com.chadrc.resourceapi.basic.crud.update;

import com.chadrc.resourceapi.core.ResourceService;
import com.chadrc.resourceapi.core.ResourceServiceThrowable;
import com.chadrc.resourceapi.core.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(method = RequestMethod.PATCH)
public class RepositoryPatchResourceService implements ResourceService<PatchRequest> {
    @Override
    public Result fulfill(Class resourceType, PatchRequest request) throws ResourceServiceThrowable {
        return null;
    }
}
